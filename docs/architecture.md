# HeySports Mobile Architecture

HeySports uses Kotlin and Compose Multiplatform to share UI, ViewModels, and data access logic across Android and iOS. The shared code follows Android's layered architecture guidance, with separate `ui`, `domain`, and `data` packages. This document describes the target architecture and its implementation order.

## Project structure

```text
androidApp/                           Android entry point, Hilt, and Firebase SDK
iosApp/                               Xcode project, iOS entry point, and Firebase SDK
shared/src/
├── commonMain/kotlin/com/example/hey_sports/
│   ├── ui/
│   │   ├── base/                     Shared presentation foundations, when needed
│   │   ├── navigation/               Routes and app navigation
│   │   ├── designsystem/             Colors, Dimens, Typography, Theme
│   │   ├── components/               UI components reused across screens
│   │   └── screens/
│   │       ├── welcome/
│   │       ├── auth/                  Account selection, sign-in, registration
│   │       └── main/
│   │           ├── matches/
│   │           ├── teams/
│   │           ├── recruitment/
│   │           ├── pitches/
│   │           └── profile/
│   ├── domain/
│   │   └── usecase/                  Cross-repository or reusable business logic
│   ├── data/
│   │   ├── model/                    App-facing models returned by repositories
│   │   ├── repository/               Repository contracts and implementations
│   │   ├── datasource/
│   │   │   ├── local/                Onboarding preferences and on-device data
│   │   │   └── remote/               Supabase Auth and PostgREST access, DTOs
│   │   └── service/                  Shared SDK client setup and configuration
│   ├── di/                           AppContainer and dependency construction
│   └── utils/                        Shared, platform-independent utilities
├── commonMain/composeResources/      Strings, images, and Compose resources
└── iosMain/                          Dependency construction and iOS adapters
```

The three layers are packages within a single `shared` Gradle module. Add packages as features need them.

Use `utils/` for small, platform-independent helpers shared across layers, such as time conversions. Keep screen formatting in `ui/`, business rules in their owning feature or use case, and DTO mappings in `data/`.

## Responsibilities and dependency direction

| Layer | Responsibility |
|---|---|
| `ui` | Compose renders immutable state and sends user actions to the ViewModel. The ViewModel calls repositories or use cases and exposes `StateFlow<UiState>`. Navigation and the design system belong to this layer. |
| `domain` | Use cases coordinate multiple repositories, share logic across ViewModels, or isolate complex business rules for testing. They depend on repository interfaces in the data layer. |
| `data` | Repositories provide access to application data, coordinate local and remote sources, and map DTOs into app-facing models. Data sources and DTOs stay within this layer. |

The UI uses `UI → Data` for straightforward operations and `UI → Domain → Data` when a use case is needed. The data layer has no dependency on the UI. For example, `LoginViewModel` calls `AuthRepository` directly. `ResolveStartDestinationUseCase` reads both onboarding completion and session state through repositories to choose the initial screen. ViewModels expose presentation state, repositories provide application data, and Welcome keeps page position in Compose pager state.

See the [Android Architecture Guide](https://developer.android.com/topic/architecture), [Architecture Recommendations](https://developer.android.com/topic/architecture/recommendations), and [Domain layer](https://developer.android.com/topic/architecture/domain-layer).

## UI layer organization

| Package | Owns |
|---|---|
| `ui/base` | Presentation foundations shared across screens, such as `BaseViewModel` or a shared UI state type when several screens use the same contract. |
| `ui/designsystem` | Colors, dimensions, typography, and theme. |
| `ui/navigation` | Route definitions, the root navigation host, and navigation between Welcome, Auth, and Main. |
| `ui/components` | Composables shared by multiple screens, such as a primary button or a common empty state. |
| `ui/screens/welcome` | Onboarding pages and their UI state. |
| `ui/screens/auth` | The account entry, sign-in, registration, and account recovery screens. |
| `ui/screens/main` | The main app shell and its feature screens, including matches, teams, recruitment, pitches, and profile. |

Keep each screen's ViewModel, UI state, and screen-specific components beside the screen. For example, `ui/screens/auth/login/` can contain `LoginScreen.kt`, `LoginViewModel.kt`, and `LoginUiState.kt`. Move a composable to `ui/components` once multiple screens need the same behavior.

Start with the multiplatform Lifecycle `ViewModel` and a screen-specific immutable `UiState`. Add `BaseViewModel.kt` or a shared state contract to `ui/base` when multiple screens need the same behavior or contract. For example, if several list screens share loading, empty, content, and error states, extract that list state once their behavior matches.

## Data layer organization

| Package | Owns | HeySports example |
|---|---|---|
| `data/model` | App-facing data types exposed by repositories. | `Match`, `Team`, `UserProfile` |
| `data/repository` | App-facing repository contracts and implementations. An implementation coordinates its data sources, maps their representations to app-facing models, and decides which source serves a read or write. | `AuthRepository`, `SupabaseAuthRepository`, `MatchRepository` |
| `data/datasource/local` | On-device storage access. Add a persistent cache when a feature needs one. Platform-protected session storage is supplied through the platform's dependency setup. | `OnboardingLocalDataSource` reads and writes the onboarding completion flag. |
| `data/datasource/remote` | Calls a remote backend and handles its request and response types. Keep feature-specific DTOs next to the data source that uses them. | `SupabaseAuthRemoteDataSource` wraps Auth calls. `MatchRemoteDataSource` wraps match queries and RPCs. |
| `data/service` | Creates and configures SDK clients shared across data sources. A data source owns its feature-specific calls to those clients. | Create one Supabase client for Auth and PostgREST. Inject that client into the relevant remote data sources. |

Split local and remote at the data-source boundary. A repository can start with a single source and take a second one when a feature requires it. Group files by feature within each package as it grows.

For example, `LoginViewModel → AuthRepository → SupabaseAuthRemoteDataSource → Supabase client`. For startup, `ResolveStartDestinationUseCase` reads onboarding completion from the onboarding repository and session state from the auth repository.

Firebase Crashlytics and Messaging are platform integrations. Their native SDK setup lives in the platform entry points and shared code can use small interfaces when it needs those capabilities.

## Dependency injection

- **Android.** Hilt in `androidApp` provides the `AppContainer` and Android-specific adapters through the application graph. The Android entry point passes the container into shared Compose.
- **iOS.** The iOS entry point constructs the same `AppContainer` type through constructor injection and supplies iOS adapters as needed. Shared dependencies live in `commonMain`.
- **ViewModels.** Define them in `commonMain` and inject repositories or use cases through their constructors. Create each ViewModel at its Compose navigation destination using a factory or initializer. The destination owns the ViewModel lifecycle, and the container owns application-scoped dependencies. Tests can pass fake repositories directly.

See [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) and [Multiplatform ViewModel](https://kotlinlang.org/docs/multiplatform/compose-viewmodel.html).

## Backend and platform services

| Service | Responsibility |
|---|---|
| **Supabase Auth** | Registration, sign-in, email verification, password recovery, and session management. Supabase Auth supplies the user ID. |
| **Supabase PostgreSQL** | Source of truth for profiles, teams, pitches, matches, listings, and participation requests. Row-Level Security (RLS) enforces access. The backend checks authorization and uses a transaction when confirming an opponent or approving the final available spot. |
| **Firebase Crashlytics** | Native SDK on each platform. Shared code uses a crash-reporting interface when needed. Local builds can supply a no-op adapter. |
| **Firebase Cloud Messaging** | Add when push notifications are needed. The backend determines recipients and sends notifications. |

Use local Supabase with migrations and seed data so Android and iOS can test against the same backend. Store sessions with platform-protected storage and keep privileged operations on the backend.

See [Supabase local development](https://supabase.com/docs/guides/local-development), [Supabase RLS](https://supabase.com/docs/guides/database/postgres/row-level-security), [Crashlytics](https://firebase.google.com/docs/crashlytics), and [FCM](https://firebase.google.com/docs/cloud-messaging).

## Implementation order

1. Move Welcome into `ui/screens/welcome/` and the design system into `ui/designsystem/`. Verify the layout and swipe behavior across all three slides and the **Explore** button (shown as **Khám phá**) on the final slide.
2. Set up `AppContainer`, Android Hilt bindings, and iOS dependency construction. Verify that both platforms can open Welcome, tap **Explore**, and reach the account selection screen.
3. Use Auth as the reference feature. Connect Compose → ViewModel → repository → local Supabase, then verify sign-in, error handling, and session restoration on both platforms.
4. Apply the same structure to teams, match scheduling, player recruitment, pitches, and profiles. Test backend authorization and concurrent requests for limited spots. Integrate Crashlytics when device QA begins.
