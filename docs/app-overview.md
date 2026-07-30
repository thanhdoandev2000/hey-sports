# HeySports app overview

Last verified against the working tree: 2026-07-30.

## Product goal

HeySports is an Android client for organizing amateur football activity in
Vietnam. It connects players and teams around fixtures, open match requests,
pitches, and lightweight community content.

## Current user journey

1. `MainActivity` waits for `AppViewModel` to resolve the start graph.
2. A first-time user sees onboarding. Completion is stored in DataStore.
3. A returning signed-out user enters the auth graph; a valid Supabase session
   goes directly to the main graph.
4. The main graph exposes Home, Maps, Team, and Profile through a bottom bar.
5. Home loads the signed-in user, upcoming matches, open match requests, live
   matches, and match weather.
6. A user can post a request to find an opponent, including pitch, time, match
   details, contact data, and uploaded photos.
7. A user can accept an open request as one of their teams or as an individual.

## Feature status

| Area | Current implementation |
| --- | --- |
| Onboarding | Functional; completion persisted with DataStore |
| Email auth | Login and registration wired to Supabase Auth |
| Google auth | Wired through Android Credential Manager and Supabase ID token |
| Facebook auth | UI is visible, but the data source is a stub |
| Password recovery | Routes exist, but destinations and click wiring are empty |
| Home | Remote upcoming/live/request data, profile header, refresh, weather |
| Find opponent | Form, validation, pitch RPC lookup, upload, Supabase insert |
| Accept match | Loads request and teams, validates selection, calls claim RPC |
| Maps | Google Map UI currently uses in-memory sample pitch data |
| Team | Rich prototype UI currently uses local/sample state and content |
| Profile | Loads current user and supports sign-out; several stats are display UI |
| News feed | UI/model exists; current state uses local `PostModel.items` |

“Functional” here describes client wiring found in this repository. The Supabase
schema, policies, functions, provider settings, and production environment are
not versioned here and require separate integration verification.

## Main source map

- App entry: `app/src/main/java/com/example/heysports/HeySportsApp.kt`
- Activity/start destination:
  `app/src/main/java/com/example/heysports/ui/features/MainActivity.kt`
- Root navigation:
  `app/src/main/java/com/example/heysports/ui/features/navigation/AppNav.kt`
- Main destinations:
  `app/src/main/java/com/example/heysports/ui/features/main/navigations/MainGraph.kt`
- Feature screens: `app/src/main/java/com/example/heysports/ui/features`
- Reusable UI: `app/src/main/java/com/example/heysports/ui/components`
- Domain contracts: `app/src/main/java/com/example/heysports/domain`
- Data and external services: `app/src/main/java/com/example/heysports/data`
- Dependency injection: `app/src/main/java/com/example/heysports/di`
- Shared utilities/events: `app/src/main/java/com/example/heysports/cores`
- Resources: `app/src/main/res`

## Non-goals and missing repository context

- There is no backend migration/schema/RLS directory in this repository.
- There is no CI workflow checked in.
- There are no product analytics, crash-reporting, or release-runbook docs.
- Instrumentation coverage is the generated context smoke test only.
- Maps, Team, and some community/profile content should be treated as prototype
  surfaces until their data contracts and interaction flows are implemented.

