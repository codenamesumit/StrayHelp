# 2. UUID primary keys

Status: Accepted

## Context
Pet IDs appear directly in public API URLs (`/api/pets/{id}`) on a
community platform open to the public.

## Decision
Use `UUID` primary keys generated via
`@GeneratedValue(strategy = GenerationType.UUID)`, not auto-increment `Long`.

## Consequences
- IDs are unguessable, so the database can't be scraped by walking
  `/1`, `/2`, `/3`.
- Safe to expose in URLs; no ID collisions across distributed inserts.
- 16 bytes vs 8, and random (v4) UUIDs can slightly fragment index order.
  Negligible at MVP scale; revisit with UUIDv7 if it ever matters.

## Alternatives considered
- **Auto-increment Long:** rejected. Sequential IDs leak record counts and
  make the entire dataset trivially enumerable on a public platform.
