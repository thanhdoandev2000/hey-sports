# Development guide

Last verified on macOS against the working tree: 2026-07-30.

## Prerequisites

- Android Studio with an Android SDK compatible with compile SDK 36.1
- JDK 11-compatible Gradle environment
- A device/emulator on API 24 or newer
- Access to the HeySports Supabase and Google configuration

The checked-in Gradle wrapper is the source of truth for Gradle itself.

## Local configuration

Copy the key names from `local.properties.example` into the existing
`local.properties` created by Android Studio, then fill values locally:

```properties
sdk.dir=/absolute/path/to/Android/sdk
SUPABASE_URL=
SUPABASE_KEY=
LOGIN_GOOGLE_KEY=
MAPS_API_KEY=
FACEBOOK_APP_ID=
FACEBOOK_CLIENT_ID=
```

Never commit populated values. `SUPABASE_KEY` must be a client-safe publishable
or anon key protected by RLS, never a service-role key.

The build currently substitutes empty values for Maps/Facebook keys, while
missing Supabase/Google keys can become the literal string `null`. A successful
compile therefore does not prove that auth, database, or Maps initialization is
correct; validate configuration on a device.

## Common commands

Run from the repository root:

```bash
# Compile production Kotlin and generated Hilt/KSP code
./gradlew :app:compileDebugKotlin

# JVM tests
./gradlew :app:testDebugUnitTest

# Build an installable debug APK
./gradlew :app:assembleDebug

# Instrumented/Compose tests with a connected emulator or device
./gradlew :app:connectedDebugAndroidTest

# Static Android checks
./gradlew :app:lintDebug
```

Known baseline on 2026-07-30:

- `:app:compileDebugKotlin`: pass
- `:app:testDebugUnitTest`: pass
- Instrumented tests/device QA: not run as part of the documentation review

## Testing map

- `app/src/test`: JVM tests. Current meaningful coverage targets date/time,
  weather JSON decoding, and match-request serialization.
- `app/src/androidTest`: device/Compose tests. Current content is only the
  generated application-context smoke test.

Prioritize new tests around:

- `safeApiCall` exception mapping and cancellation
- ViewModel loading/error transitions
- create/accept match validation and request mapping
- repository/data-source behavior with fake dependencies
- typed navigation and saved-state refresh
- Maps/Team behavior when their real data layers are introduced

## Manual smoke test

For changes that touch a complete user flow:

1. Fresh install: onboarding → login/register.
2. Relaunch: start graph matches persisted onboarding/session state.
3. Home: loading, refresh, empty, error, upcoming/live/request sections.
4. Create request: validation, pitch search, photo upload, success and failure.
5. Accept request: own-request rejection, individual/team claim, closed request.
6. Maps: key initialization, map rendering, marker selection, search/sheet.
7. Profile: profile loading and sign-out back-stack behavior.
8. Offline/cancel/retry: no stuck loading indicators or stale navigation.

Record device model/emulator, API level, build variant, and backend environment
in the handoff.

## Documentation maintenance

- New feature or changed status: update `docs/app-overview.md`.
- Package/data-flow change: update `docs/architecture.md`.
- Table/view/RPC/DTO/auth/storage change: update `docs/backend-contracts.md`.
- Setup/command/test change: update this file.
- Fixed or newly accepted technical debt: update `docs/code-review.md`.

