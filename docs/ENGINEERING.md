# StrayHelp — Engineering Brief

Community platform to report stray animals, rehome pets, and coordinate rescues.
This file is the source of truth for how the project is built. Keep it current.

> Loaded by Claude Code via a one-line `CLAUDE.md` at repo root: `@docs/ENGINEERING.md`

## Project intent
- Portfolio **and learning** project. Author knows Java, is learning Spring Boot.
- **Add teaching comments** on non-obvious code (what each annotation/line does). Do not strip them.
- Prefer clear, explicit code over clever shortcuts.

## Tech stack (fixed versions — verify against docs, do not assume)
- Backend: Spring Boot **4.1.0**, Java **21**, JPA/Hibernate, Lombok, Bean Validation
- Auth (Phase 4+): Spring Security + JWT
- Frontend (Phase 6): Next.js + plain JavaScript (NO TypeScript) + Tailwind
- DB: Supabase-hosted Postgres **17.6**, Session Pooler (IPv4)
- Images (Phase 7): Cloudinary

## How to run (backend)
    cd backend
    ./mvnw spring-boot:run     # Windows: mvnw.cmd spring-boot:run
Requires a `.env` file (gitignored) with `DB_PASSWORD`. Runs on port 8080.

## Conventions
- Build order per feature (vertical slice): Entity → Repository → Service → Controller → Postman test
- Commits: Conventional Commits — feat:, fix:, docs:, refactor:
- Decisions recorded as short ADRs in docs/adr/

## Version gotchas (Boot 4.x — break old tutorials)
- Starter is `spring-boot-starter-webmvc` (old `spring-boot-starter-web` renamed). Test starters are modular (-webmvc-test, -data-jpa-test, etc.).
- Jackson 3: databind moved to `tools.jackson.*`. BUT annotations (@JsonIgnore, @JsonProperty, @JsonFormat) stayed at `com.fasterxml.jackson.annotation`. Exception: @JsonSerialize/@JsonDeserialize moved to `tools.jackson.databind.annotation`.
- Spring Security 7.1: WebSecurityConfigurerAdapter deleted; antMatchers → requestMatchers; .and() chaining removed. Pre-2023 tutorials won't compile. Phase 4 uses official docs only.

## Key decisions
- Spring Security dependencies commented out in pom.xml until Phase 4 (on classpath they lock all endpoints behind HTTP Basic and block Postman testing in Phases 1–3).
- spring.jpa.open-in-view: false — surfaces lazy-loading bugs immediately instead of hiding them.
- ddl-auto: update for MVP — only adds columns, never drops/alters. Type changes require manually dropping the table in Supabase. Switch to validate + Flyway post-MVP.
- Supabase Session Pooler (not Direct Connection) — IPv4 access from home/office laptops.

## Roadmap (8-phase MVP)
1. Pet entity + basic create/read, no auth  ← CURRENT
2. Full Pet CRUD + validation → throwaway mini frontend spike
3. User accounts + BCrypt, no JWT
4. JWT + Spring Security (hardest — go slow)
5. Pet↔User relationships + admin moderation
6. Frontend: 6 pages (home, browse, detail, post, auth, profile)
7. Cloudinary images
8. Polish + deploy
Deferred post-MVP: Docker/K8s, S3/CloudFront, Redis, CI/CD, AI breed detection, chat, donations, NGO dashboards, map search, verification badges.

## Data model decision (resolved)
One `Pet` entity with a status enum. Chosen over splitting into two entities (report vs listing) for a simpler, faster, learning-focused MVP. A stray report and an adoption listing are the same Pet, distinguished by status. Splitting can be revisited post-MVP.
Pet fields (draft): id, name (nullable), species, breed (nullable), age (nullable), gender, size, description, location, contactPhone, status, imageUrl (nullable), createdAt, updatedAt.
