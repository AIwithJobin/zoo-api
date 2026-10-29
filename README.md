<h1 align="center">🦁 Zoo API</h1>

<p align="center">
  A clean, layered REST API in <b>Kotlin + Spring Boot 4</b> for managing zoo animals:
  validated DTOs, <b>RFC 9457 problem details</b>, PostgreSQL via Spring Data JPA, Docker Compose, and tests at unit and HTTP level.
</p>

<p align="center">
  <a href="https://github.com/AIwithJobin/zoo-api/actions/workflows/ci.yml"><img src="https://github.com/AIwithJobin/zoo-api/actions/workflows/ci.yml/badge.svg" alt="CI"/></a>
  <img src="https://img.shields.io/badge/Kotlin%202.2-7F52FF?style=flat-square&logo=kotlin&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring%20Boot%204-6DB33F?style=flat-square&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Java%2021-ED8B00?style=flat-square&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=flat-square&logo=postgresql&logoColor=white"/>
  <img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white"/>
  <img src="https://img.shields.io/badge/License-MIT-green?style=flat-square"/>
</p>

---

## ✨ Highlights

- **Layered design:** controller → service → repository, organised by feature (`animal/`)
- **DTOs, not entities, at the edge:** `AnimalRequest` is validated with Jakarta Bean Validation; `AnimalResponse` is what clients see
- **Type-safe domain:** enums for sex, diet, health and IUCN conservation status; `LocalDate` and `LocalTime` instead of strings
- **Consistent errors:** every error is an RFC 9457 `application/problem+json` response (`404` unknown id, `400` invalid input)
- **REST conventions:** `201 Created` with a `Location` header, `204 No Content` on delete, id-based `PUT` and `DELETE`, a versioned `/api/v1` path
- **Config via environment:** database credentials come from `DB_URL`, `DB_USERNAME` and `DB_PASSWORD`; nothing secret is committed
- **Runs in one command:** `docker compose up` starts PostgreSQL and the API with demo data
- **Tested:** MockK unit tests for the service and MockMvc HTTP tests against in-memory H2, run in CI on every push

## 🏗️ Architecture

```
HTTP ─► AnimalController ─► AnimalService (@Transactional) ─► AnimalRepository (Spring Data JPA) ─► PostgreSQL
              │                     │
       @Valid AnimalRequest   AnimalNotFoundException ─► ApiExceptionHandler ─► ProblemDetail (RFC 9457)
```

```
src/main/kotlin/dev/jobinjose/zoo/
├── animal/
│   ├── Animal.kt                 # JPA entity + domain enums
│   ├── AnimalDtos.kt             # request/response DTOs and mappers
│   ├── AnimalRepository.kt
│   ├── AnimalService.kt
│   ├── AnimalController.kt
│   └── AnimalNotFoundException.kt
├── common/ApiExceptionHandler.kt # problem-detail error mapping
└── config/SampleDataLoader.kt    # seeds demo animals on first start
```

## 🔌 Endpoints

Base URL: `http://localhost:9000/api/v1/animals`

| Method | Path | Description | Responses |
|---|---|---|---|
| `GET` | `/` | List animals (optional `?species=lion`) | `200` |
| `GET` | `/{id}` | Get one animal | `200`, `404` |
| `POST` | `/` | Create an animal | `201` + `Location`, `400` |
| `PUT` | `/{id}` | Replace an animal | `200`, `400`, `404` |
| `DELETE` | `/{id}` | Delete an animal | `204`, `404` |

**Create request**
```json
{
  "name": "Oscar", "species": "Orangutan", "age": 12, "sex": "MALE",
  "habitat": "Rainforest House", "enclosureId": "ENC-402", "diet": "OMNIVORE",
  "weightKg": 85.0, "heightCm": 140.0, "healthStatus": "HEALTHY",
  "conservationStatus": "CRITICALLY_ENDANGERED", "arrivalDate": "2017-03-18",
  "feedingTime": "12:30", "caretaker": "David Lee"
}
```

**Error response** (`404`)
```json
{ "type": "about:blank", "title": "Animal not found", "status": 404, "detail": "Animal with id 99 was not found", "instance": "/api/v1/animals/99" }
```

## 🚀 Run it

**Option A: Docker Compose (database + API)**
```bash
docker compose up --build
curl http://localhost:9000/api/v1/animals
```

**Option B: local JDK 21 + your own PostgreSQL**
```bash
export DB_URL=jdbc:postgresql://localhost:5432/zoo DB_USERNAME=zoo DB_PASSWORD=zoo
./gradlew bootRun            # Windows: gradlew.bat bootRun
```

## 🧪 Tests

```bash
./gradlew test
```

| Test | Level | Covers |
|---|---|---|
| `AnimalServiceTest` | Unit (MockK) | filtering, not-found handling, update and delete rules |
| `AnimalControllerTest` | HTTP (MockMvc + H2) | status codes, `Location` header, validation, problem details |
| `ZooApplicationTests` | Smoke | Spring context starts |

---

<p align="center">Built by <a href="https://github.com/AIwithJobin">Jobin Jose</a> · <a href="https://linkedin.com/in/jobin--jose">LinkedIn</a> · MIT License</p>
