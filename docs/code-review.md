# Code review baseline

Review date: 2026-07-30.

Scope: static review of the Android client plus successful
`:app:testDebugUnitTest` and `:app:compileDebugKotlin`. The worktree already
contained uncommitted Maps, Team, Profile, shared component, resource, and IDE
changes; the review did not modify them. Device QA and backend policy/schema
inspection were out of scope.

## What is working well

- The app has a recognizable UI/domain/data/DI separation.
- Typed Navigation Compose routes avoid string route construction.
- Hilt bindings generally expose domain repository contracts to ViewModels.
- Compose state is usually immutable and collected lifecycle-aware.
- Parallel Home and accept-match loading has explicit completion cleanup.
- Date/time, weather decoding, and insert serialization have meaningful JVM
  tests beyond the generated sample test.
- Credentials are loaded from ignored local properties rather than hardcoded in
  Kotlin source.

## Findings

### P1 — Coroutine cancellation is converted into a network error

`data/networks/safeCallApi.kt` catches `Throwable` and does not re-throw
`CancellationException`. Screen disposal, ViewModel clearing, or a cancelled
parent can therefore continue through `NetworkResult.Error`, emit misleading
global errors, and delay structured cancellation.

Recommended change: catch and immediately re-throw `CancellationException`
before mapping expected transport exceptions. Add a coroutine cancellation unit
test for the wrapper.

### P1 — Facebook button reports a successful login without authenticating

`AuthDataSource.signInWithFacebook` returns `null` inside `safeApiCall`, which is
a successful `NetworkResult`. `LoginViewModel.loginFacebook` navigates to Home
on every success, while the login screen exposes the Facebook button.

Recommended change: hide/disable the button until implemented, or return an
explicit unsupported error. Implementing the provider also requires manifest,
Supabase provider, token exchange, and device-flow validation.

### P1 — Backend security contracts are not versioned with the client

The client relies on Supabase RLS, several views, two RPCs, and Storage policies,
but no schema/migration/policy source exists here. The atomicity and authorization
of accepting a match cannot be reviewed from this repository.

Recommended change: add/link the backend repository or check in Supabase
migrations and tests. Treat `claim_match_request` and `users`/Storage RLS as the
first security review targets.

### P2 — Missing local configuration can compile as invalid runtime values

`app/build.gradle.kts` interpolates missing `SUPABASE_URL`, `SUPABASE_KEY`, and
`LOGIN_GOOGLE_KEY` values directly; absent properties become the literal
`"null"`. There is no early debug-build validation.

Recommended change: provide safe empty defaults plus a clear validation task or
fail fast for variants that require live services. Never validate by logging the
credential value.

### P2 — Visible auth actions are unfinished

Forgot/reset password destinations are empty and the forgot-password callback
from the login graph is empty. Guest/“without login” UI also has a default empty
callback. These interactions appear actionable but do nothing.

Recommended change: hide unfinished actions or implement explicit “coming soon”
feedback until the flows are complete.

### P2 — Production-looking Maps and Team screens use sample state

Maps calls `samplePitches()` and does not connect search/current-location actions
to repositories or permissions. Team toggles local `hasTeam` state and renders
sample teams/statistics/activity. Users can mistake prototypes for persisted
features.

Recommended change: mark prototypes clearly in debug/product scope, then add
ViewModels, repository contracts, loading/empty/error states, and tests before
shipping them as live features.

### P2 — Automated behavior coverage is thin

There are four JVM test files and one generated instrumentation smoke test for a
client with auth, navigation, multiple ViewModels, remote calls, Maps, uploads,
and transactional match acceptance.

Recommended order: cancellation/error mapping, match-create/claim validators,
ViewModel state transitions, repository tests, then focused Compose navigation
and interaction tests.

### P3 — Dependency and configuration cleanup is needed

The version catalog contains unused/dynamic-looking entries such as
`facebookLogin = "latest.release"`, and `app/build.gradle.kts` includes duplicate
or overlapping Compose/DataStore dependencies. Dynamic versions reduce build
reproducibility even when currently unused.

Recommended change: run a dependency usage audit, remove dead aliases and
duplicates, and keep Supabase BOM/version ownership in one consistent place.

### P3 — Localization is inconsistent

Most recent UI uses resources, but shared/older composables and ViewModels still
contain Vietnamese literals and some English labels. Error mapping is also
hardcoded in the network layer.

Recommended change: move user-visible strings to resources when touching each
feature and define whether the product is Vietnamese-only or multilingual.

## Suggested delivery order

1. Fix cancellation and false-success auth behavior.
2. Version and test Supabase migrations/RLS/RPC/storage policies.
3. Validate local/runtime configuration safely.
4. Decide which prototype features are in the next release and back them with
   real data or label/hide them.
5. Add ViewModel/repository/Compose coverage around the chosen release flows.
6. Clean dependencies and localization incrementally.

