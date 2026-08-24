# 3. Enums for constrained fields

Status: Accepted

## Context
`gender`, `size`, and `status` each have a small, fixed set of valid values.

## Decision
Model them as Java enums (`Gender`, `PetSize`, `PetStatus`), persisted with
`@Enumerated(EnumType.STRING)` rather than free-text `String` columns.

## Consequences
- Invalid values are impossible at compile time — no typo drift like
  "male" / "Male" / "M" splitting one concept across rows.
- Filtering and grouping stay reliable; the DB stores readable names.
- Adding a value needs a code change + redeploy. Acceptable — these sets
  are stable.
- `EnumType.STRING` (not ORDINAL) chosen deliberately: storing names means
  reordering or inserting enum constants can't corrupt existing rows.

## Alternatives considered
- **Free-text String:** rejected. Typo-prone, breaks filtering, no
  compile-time safety.
- **EnumType.ORDINAL:** rejected. Stores 0/1/2; reordering constants later
  silently changes the meaning of already-saved data.
