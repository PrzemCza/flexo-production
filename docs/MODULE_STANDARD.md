# Standard modułu domenowego – Flexo Backend

## Struktura pakietu `com.flexo.<domain>`

Każdy moduł domenowy (np. `ink`, `polymer`, `rawmaterial`, `diecut`, `productionmonitor`) powinien zawierać:

```
com.flexo.<domain>/
├── controller/   – REST API (kontrakty zgodne z frontem)
├── service/      – logika biznesowa
├── repository/   – Spring Data JPA
├── dto/          – kontrakty zewnętrzne (request/response)
├── model/        – encje JPA
├── mapper/       – mapowanie DTO ↔ model
└── spec/         – (opcjonalnie) specyfikacje zapytań JPA
```

## Warstwy

### Controller

- `@RestController`, `@RequestMapping("/api/<resource>")` (np. `/api/inks`, `/api/die-cuts`).
- Wstrzykiwanie: `@RequiredArgsConstructor` + `private final XxxService service`.
- CRUD: `GET`, `GET /{id}`, `POST`, `PUT /{id}`, `DELETE /{id}`. Lista: zwracać `Page<XxxResponse>` przy stronicowaniu, `List<XxxResponse>` tylko gdy brak paginacji.
- Walidacja: `@Valid @RequestBody` przy tworzeniu i aktualizacji.
- Odpowiedzi: `ResponseEntity.ok(...)`, `ResponseEntity.noContent()` przy DELETE, `ResponseEntity.status(HttpStatus.CREATED)` przy POST (opcjonalnie).
- CORS: konfiguracja globalna w jednym miejscu (np. `CorsConfig`) zamiast powielania `@CrossOrigin` w każdym kontrolerze.

### Service

- `@Service`, wstrzykiwanie repozytorium i ewentualnie mapper.
- Metody: `create(Request)`, `getById(id)`, `update(id, Request)`, `delete(id)`. Lista: `getAll()` lub `getFiltered(...)` / `search(filter, pageable)`.
- **Zasób nie znaleziony**: zamiast `RuntimeException` używać `com.flexo.common.exception.ResourceNotFoundException` – GlobalExceptionHandler zwróci 404.
- **Konflikt / reguły biznesowe**: używać `com.flexo.common.exception.ConflictException` dla 409.
- Transakcje: przy zapisie (create/update/delete) warstwa serwisu jest domyślnie transakcyjna (`@Transactional` na klasie lub metodzie, jeśli wymagane).

### Repository

- Interfejs rozszerzający `JpaRepository<Entity, Long>`.
- `@Repository` (opcjonalnie, Spring i tak rejestruje).

### DTO

- **Request** (create/update): record lub klasa z walidacją (`jakarta.validation`). Nazewnictwo: `CreateXxxRequest` lub `XxxRequest` (spójnie w całym projekcie).
- **Response**: record z polami tylko do odczytu, np. `XxxResponse`.
- **Filter** (gdy potrzebne): record/klasa z opcjonalnymi polami filtrów, np. `XxxFilter`.

### Model (encje)

- `@Entity`, `@Table(name = "...")`, `@Id`, `@GeneratedValue(strategy = GenerationType.IDENTITY)`.
- Nazwy klas w PascalCase (np. `Ink`, nie `ink`).

### Mapper

- Klasa z metodami statycznymi (lub bean, jeśli wstrzykiwany): `toEntity(Request)` → Entity, `toResponse(Entity)` → Response.
- Brak logiki biznesowej – tylko mapowanie pól.

### Spec (opcjonalnie)

- Gdy moduł ma wyszukiwanie z wieloma kryteriami: pakiet `spec` z klasą `XxxSpecification` i metodami budującymi `Specification<Entity>` (np. `build(XxxFilter)` lub osobne metody łączone w serwisie).

## Pakiet `common`

- **exception**: wyjątki współdzielone – `ResourceNotFoundException`, `ConflictException`. Serwisy powinny je rzucać zamiast generycznego `RuntimeException`.
- **globalexceptionhandler**: `GlobalExceptionHandler` z `@RestControllerAdvice` i spójnym `ErrorResponse` (status, message, opcjonalnie errors, timestamp).
- Nie umieszczać w `common` logiki domenowej; tylko wyjątki, handler i ewentualnie wspólne konfiguracje (CORS, format dat).

## Konwencje nazewnicze

- Endpointy: RESTful, liczba mnoga, kebab-case: `/api/raw-materials`, `/api/die-cuts`.
- DTO: `<Domain>Request`, `<Domain>Response`, `Create<Domain>Request` (wybór jednego stylu w całym projekcie).
- Encje: PascalCase, np. `Ink`, `DieCut`, `RawMaterial`.
