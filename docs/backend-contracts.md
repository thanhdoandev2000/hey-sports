# Backend and external contracts

Last verified against the Android client: 2026-07-30.

This file records what the client expects. It is not a replacement for Supabase
migrations, RLS policies, RPC definitions, provider settings, or API monitoring.
Those backend artifacts are not present in this repository.

## Supabase

The client installs Auth, PostgREST, Storage, and Realtime. The current code
expects the following objects:

| Kind | Name | Client use |
| --- | --- | --- |
| Table/view | `users` | Decode the current profile as `UserDto` |
| View | `get_upcoming_matches` | Home upcoming match list |
| View | `match_requests_feed` | Home open match request feed |
| View | `match_requests_details` | Single request filtered by `id` |
| View | `my_teams` | Teams eligible to accept a request |
| View | `get_matches_live` | Home live matches |
| Table | `match_requests` | Insert `MatchRequestInsertDto` |
| RPC | `get_pitches` | Search pitches using parameter `search` |
| RPC | `claim_match_request` | Atomically accept and close/create a match |
| Storage bucket | `hey-sports` | Upload match-request photos |

### `claim_match_request` parameters

- `p_match_request_id`: required request id
- `p_applicant_team_id`: nullable team id for individual claims
- `p_message`: nullable message
- `p_contact_phone`: nullable phone number

The RPC should enforce authorization, request ownership, open status, duplicate
claims, team eligibility, and the final transaction. Client-side validation is
only user feedback and is not a security boundary.

### Auth assumptions

- Email/password login and signup are enabled.
- Google ID-token login is configured for the server client id supplied by
  `LOGIN_GOOGLE_KEY`.
- Session persistence and refresh are handled by the Supabase Auth client.
- Facebook login is not implemented even though a button is exposed.
- Password recovery/reset destinations are not implemented.
- RLS must limit `users` reads to a single appropriate row because the client
  currently performs an unfiltered select followed by `decodeSingleOrNull`.

### Storage assumptions

Uploads use `heysports/<timestamp>.jpg`, `upsert = true`, and a signed URL with a
one-year expiry. Backend policy must restrict writes to authenticated users and
prevent cross-user overwrite/read abuse. A timestamp alone is not a strong
collision-resistant or ownership-aware file key.

## Open-Meteo

Weather uses:

```text
GET https://api.open-meteo.com/v1/forecast
```

Parameters include latitude, longitude, hourly temperature/weather code/rain
probability, UTC timezone, and a 16-day forecast. Match timestamps are normalized
to the corresponding UTC hour before lookup.

## Google services

- Google Maps reads `MAPS_API_KEY` through an Android manifest placeholder.
- Google Sign-In uses Android Credential Manager and `LOGIN_GOOGLE_KEY`.
- `google-services.json` is present for the Android app; do not print or copy it
  into documentation, issues, or logs.

## Contract change checklist

When changing a DTO, view, table, RPC, bucket, or auth provider:

1. Update the backend migration/policy/function in the backend source of truth.
2. Update DTO serialization names and nullability.
3. Update the data source and domain mapping.
4. Add serialization/mapping/repository tests.
5. Test success, empty, unauthorized, conflict, offline, and cancellation paths.
6. Update this file and `docs/app-overview.md`.

