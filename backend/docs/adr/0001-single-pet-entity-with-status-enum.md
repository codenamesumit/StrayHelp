# 1. Single Pet entity with status enum

Status: Accepted

## Context
StrayHelp handles both stray reports and adoption listings. These could be
modelled as two separate entities, or as one entity representing the same
animal at different points in its lifecycle.

## Decision
Use a single `Pet` entity, distinguished by a `PetStatus` enum
(REPORTED → VERIFIED → RESCUED → AVAILABLE → ADOPTED, plus CLOSED).
A stray report and an adoption listing are the same animal at different
stages, not different things.

## Consequences
- One table, simpler queries, and one row follows an animal end-to-end.
- The animal's full history stays unified — no copying data between tables
  when a rescued stray becomes adoptable.
- Some fields are only meaningful at certain stages, so they're nullable.
- Invalid status transitions must be guarded in code later (service layer).

## Alternatives considered
- **Two entities (StrayReport + AdoptionListing):** rejected. Duplicates
  shared fields, requires linking/copying data at the rescue→adoption
  handoff, and fragments a single animal's history across two tables.
