# Architecture

Last verified against the working tree: 2026-07-30.

## Runtime shape

```text
Compose route/screen
        |
        v
ViewModel + immutable UiState / UiEffect
        |
        v
domain repository contract
        |
        v
data repository implementation
        |
        v
Supabase / Storage / Ktor / DataStore data source
```

Hilt assembles the graph. `BaseViewModel` owns state updates, one-off effects,
parallel API coordination, loading reduction, and global error events.
`AppNavigation` observes the global event bus and renders shared error UI.

## Package responsibilities

### `ui`

- `ui/features`: routes, graphs, screens, feature ViewModels, feature-local
  state/effects, and local components.
- `ui/components/cores`: reusable `JP*` design primitives.
- `ui/components/app`: reusable HeySports-specific components.
- `ui/base`: `BaseViewModel`, `UiState`, `UiEffect`, and `HeySportContainer`.
- `ui/theme`: colors, dimensions, fonts, typography, and Material theme.

Public route composables should look up/collect a ViewModel and wire navigation.
Private screen composables should receive state and callbacks so previews and
Compose tests remain possible.

### `domain`

- `domain/models`: app-facing models independent of transport details.
- `domain/repositories`: contracts consumed by ViewModels.

Do not introduce Supabase query types, Android UI types, or storage mechanics
into domain contracts unless an existing contract already requires an Android
type and the task explicitly includes removing that coupling.

### `data`

- `data/models/dto`: serialized payloads and DTO-to-domain conversion.
- `data/models/enums`: server/UI option values.
- `data/models/response`: `NetworkResult` and parallel request wrappers.
- `data/sources/remote`: Supabase Auth/PostgREST/RPC/Storage and Open-Meteo.
- `data/sources/local`: DataStore and future local persistence.
- `data/repositories`: thin implementations of domain contracts.
- `data/networks`: shared remote-call handling.

### `di` and `cores`

`di` provides dispatchers, JSON, Ktor, Supabase, Credential Manager options, and
repository bindings. `cores` contains shared events, extensions, validators,
date/time helpers, constants, and annotations.

## Navigation

Navigation uses Kotlin-serializable typed route objects.

```text
OnBoardingGraph -> GettingRoute
AuthGraph       -> LoginRoute -> RegisterRoute
MainGraph       -> HomeRoute
                -> MapsRoute
                -> TeamRoute
                -> ProfileRoute
                -> PostOpponentRoute
                -> AcceptMatchRoute(matchRequestId)
```

`AppViewModel` selects the root graph from the onboarding DataStore flag and the
Supabase session. A successful match claim uses `SavedStateHandle` to signal a
Home refresh after popping the detail route.

## State and effects

- Feature state is a `data class` implementing `UiState`.
- One-off local events implement `UiEffect` and are emitted through the
  `BaseViewModel` channel.
- Cross-feature errors/toasts use `AppEventBus`.
- State changes use `updateState { copy(...) }`.
- Screen flows are collected with `collectAsStateWithLifecycle`.
- One-time loads are normally launched from `LaunchedEffect`.

When parallel calls use `callApis`, every success, error, and completion branch
must settle its feature-specific loading flags. Cancellation must be re-thrown,
including inside lower-level network wrappers.

## Data flow examples

### Home

`Home` → `HomeViewModel` → `AuthRepository`, `MatchesRepository`, and
`WeatherRepository` → Supabase/Open-Meteo. The initial calls run in parallel;
weather is then requested for upcoming matches that contain coordinates.

### Create match request

`FindOpponent` → `MatchRequestViewModel` validates user/time/pitch → optional
photo upload to Supabase Storage → `MatchesRepository.createMatchRequest` →
insert into `match_requests`.

### Accept match request

`AcceptMatch` → `AcceptMatchViewModel` loads request and eligible teams in
parallel → validates owner/status/team/phone → calls `claim_match_request` RPC →
emits `Submitted` → main graph returns to Home and requests refresh.

## Architectural constraints

- Backend identifiers and enum strings are contracts; centralize or map them
  before changing names.
- Signed upload URLs currently expire after one year. Do not assume they are
  permanent public URLs.
- `users` profile visibility depends on Supabase RLS; the client repository does
  not contain policy definitions.
- Maps and Team currently mix presentation with sample data. Introduce their
  ViewModel/repository paths before treating them as production-backed features.

