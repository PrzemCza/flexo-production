# Backend structure audit – Flexo

## Scope

Modules: `ink`, `polymer`, `rawmaterial`, `diecut`, `productionmonitor`. Package `common`.

## Current structure per module

| Layer       | ink | polymer | rawmaterial | diecut | productionmonitor |
|------------|-----|---------|-------------|--------|--------------------|
| controller | ✓   | ✓       | ✓           | ✓      | ✓                  |
| service    | ✓   | ✓       | ✓           | ✓      | ✓                  |
| repository | ✓   | ✓       | ✓           | ✓      | ✓                  |
| dto        | ✓   | ✓       | ✓           | ✓      | ✓                  |
| model      | ✓   | ✓       | ✓           | ✓      | ✓                  |
| mapper     | ✓   | ✓       | ✓           | ✓      | ✓                  |
| spec       | —   | —       | ✓           | ✓      | —                  |
| config     | —   | —       | —           | ✓ (Cors) | —                |

## Repeating patterns

### Controllers

- **RequestMapping**: `/api/<resource>` (inks, polymers, raw-materials, die-cuts, production-orders).
- **CRUD**: GET (list + `/{id}`), POST, PUT `/{id}`, DELETE `/{id}`.
- **Injection**: Mix of `@RequiredArgsConstructor` (ink, polymer, productionmonitor) and explicit constructor (rawmaterial, diecut).
- **CrossOrigin**: Present on ink, polymer, productionmonitor; missing on rawmaterial, diecut (may rely on global CorsConfig).
- **Validation**: `@Valid @RequestBody` on create/update where checked (ink, polymer, diecut, productionmonitor); rawmaterial has `@Valid` as well.
- **Return types**: `ResponseEntity<T>`, list vs `Page<T>`: ink/rawmaterial return list; polymer/diecut/rawmaterial search return `Page`.

### Services

- **CRUD**: create(request), getById(id), update(id, request), delete(id). List: getAll() or getFiltered(…) / search(filter, pageable).
- **Not found**: All use `new RuntimeException("X not found: " + id)` instead of `ResourceNotFoundException` from common. GlobalExceptionHandler handles only `ResourceNotFoundException` → 404; runtime exceptions fall through to generic 500.
- **Mappers**: All use static `Mapper.toEntity(request)` and `Mapper.toResponse(entity)`.
- **Specs**: Only rawmaterial and diecut use specifications; build pattern in rawmaterial (single `build(Filter)`), diecut combines specs in service.

### DTOs

- **Request**: Records: `CreateInkRequest`, `CreatePolymerRequest`, `CreateRawMaterialRequest`, `CreateDieCutRequest`. Class with Lombok: `ProjectOrderRequest` (no "Create" prefix).
- **Response**: Records: `InkResponse`, `PolymerResponse`, `RawMaterialResponse`, `DieCutResponse`, `ProjectOrderResponse`.
- **Filter**: Only rawmaterial has a dedicated `RawMaterialFilter` DTO; diecut uses many `@RequestParam`s.

### Mappers

- Same pattern: static `toEntity(Request)` and `toResponse(Entity)`. No shared interface or base.

### Models (entities)

- JPA `@Entity`, `@Table`, `@Id` with `GenerationType.IDENTITY`. One naming inconsistency: `ink` (lowercase) in package `com.flexo.ink.model`; all others PascalCase (e.g. `DieCut`, `RawMaterial`).

### Common package

- **exception**: `ResourceNotFoundException`, `ConflictException` – used by GlobalExceptionHandler.
- **globalexceptionhandler**: `GlobalExceptionHandler` with `ErrorResponse` record; handles validation (400), not found (404), conflict (409), data integrity (409), generic (500).
- **Gap**: No `BaseEntity`, no shared `BaseSpecification` or filter utilities; services do not use `ResourceNotFoundException`/`ConflictException` consistently.

## Inconsistencies and recommendations

1. **Not found handling**: Replace `RuntimeException("X not found")` with `ResourceNotFoundException` in all services so the API returns 404 and a consistent error body.
2. **Controller style**: Unify on `@RequiredArgsConstructor` and explicit `@CrossOrigin` or a single global CORS config.
3. **DTO naming**: Use `<Domain>Request` / `<Domain>Response` (or keep `CreateXRequest` for create/update). Align `ProjectOrderRequest` with the rest (e.g. keep as request DTO name).
4. **Entity naming**: Rename `ink` to `Ink` for consistency.
5. **Pagination**: Ink and rawmaterial list endpoints return full list; frontend may expect `Page`. Align with frontend contract (see align-front-back-contracts).
6. **Filter DTOs**: DieCut controller has many params; consider a single `DieCutFilter` DTO like RawMaterialFilter for consistency and future extensibility.
7. **Common**: Add minimal shared pieces only where duplication is high (e.g. base exception usage doc; optional base for specs later).

## Summary

Structure is already close to a consistent **controller → service → repository** plus **dto / model / mapper** (and **spec** where needed). Main improvements: use common exceptions for 404/409, unify controller style and CORS, fix entity name `ink`, and align DTO/pagination with frontend.
