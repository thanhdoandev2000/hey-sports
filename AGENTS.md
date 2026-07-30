# HeySports Agent Guide

This file applies to the entire repository. It is the entry point for agents and
contributors; detailed facts live in `docs/` and should not be duplicated here.

## Product and stack

HeySports is a Vietnamese Android app for amateur football communities. The
current client supports onboarding, Supabase authentication, a home feed with
upcoming/live matches and open match requests, creating and accepting a match
request, pitch discovery, team/profile surfaces, photo upload, and match weather.

The repository is a single `:app` module using Kotlin, Jetpack Compose, Material
3, Navigation Compose, Hilt/KSP, Supabase, DataStore, Google Maps, Coil, and
Ktor. The package root is `com.example.heysports`; minimum SDK is 24.

## Read before changing code

When the `implement-hey-sports-tasks` Codex skill is available, use it for
feature implementation, UI changes, bug fixes, refactoring, and business-logic
changes. This repository guide and `docs/` provide its current local context;
do not treat bundled skill text as the source of changing project facts.

Choose the smallest set that covers the task:

- Product scope and implementation status: `docs/app-overview.md`
- Package boundaries and runtime data flow: `docs/architecture.md`
- Supabase views, tables, RPCs, storage, auth, and external APIs:
  `docs/backend-contracts.md`
- Local setup, configuration, build, test, and validation:
  `docs/development.md`
- Known findings and prioritized technical debt: `docs/code-review.md`

When documentation disagrees with executable code, code is the source of truth.
Update the affected document in the same change.

## Working rules

1. Inspect the full vertical slice before editing: route/screen, ViewModel/state,
   domain repository, implementation, data source/DTO, DI, resources, and tests.
2. Preserve the existing layers:
   - `ui/features` and `ui/components` own Compose and presentation state.
   - `domain` owns app-facing models and repository contracts.
   - `data` owns DTOs, Supabase/Ktor/storage calls, and implementations.
   - `di` owns Hilt bindings/providers.
   - `cores` owns cross-cutting utilities, extensions, and app events.
3. Prefer existing primitives: `BaseViewModel`, `UiState`, `UiEffect`,
   `NetworkResult`, `safeApiCall`, `HeySportContainer`, `JP*` components, theme
   tokens, typed routes, and Hilt injection.
4. Keep state immutable and collect flows with `collectAsStateWithLifecycle`.
   Re-throw `CancellationException`; never convert cancellation into an error.
5. Put user-visible strings in resources. Reuse theme dimensions/colors and
   existing components before adding literals or a new design primitive.
6. Keep Supabase details in data sources. ViewModels depend on domain repository
   contracts, not on Supabase clients or DTO query mechanics.
7. Do not add credentials, tokens, service-account material, or populated
   `local.properties` files. Treat `google-services.json` as sensitive project
   configuration and do not print its contents.
8. The worktree may contain user changes. Inspect `git status`, avoid unrelated
   formatting/rewrites, and never discard changes outside the task.
9. Use the version catalog for dependencies. Avoid dynamic versions and explain
   any new dependency.
10. Add or update tests for validators, date/time logic, DTO serialization,
    mappers, reducers, and repository behavior when those areas change.

## Validation

Run the narrowest useful task from the repository root:

```bash
./gradlew :app:compileDebugKotlin
./gradlew :app:testDebugUnitTest
./gradlew :app:assembleDebug
```

- Kotlin-only production changes: compile, plus focused/unit tests.
- Resources, manifest, DI, Gradle, or generated code: assemble debug.
- Compose interaction, navigation, permissions, Maps, camera, or auth provider
  changes: add emulator/device QA and report the device/API used.
- Documentation-only changes: verify links, paths, commands, and current code
  facts; a build is optional unless the docs describe an unverified baseline.

## Definition of done

- The requested behavior or documentation is complete and scoped.
- Loading, refresh, empty, error, and cancellation paths were considered.
- Relevant tests/build tasks pass, or the exact blocker is reported.
- No secrets or unrelated user changes were introduced.
- `docs/` and this guide remain accurate for any changed contract or structure.
