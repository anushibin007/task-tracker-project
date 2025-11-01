# Task Tracker

## Overview

Short goal: Implement a REST API to manage tasks with fields: id, title, description, status, priority, dueDate, createdAt, updatedAt. Provide robust validation, standardized errors, basic auth for writes, and OpenAPI docs.

### Todos

-   [ ] Confirm language and build tool (Java + Maven/Gradle, Java 17+)
-   [ ] Create repository and initial project scaffold
-   [ ] Add this README to repo root and keep it updated

---

## Quick start

Example (Maven + Java 17):

1. Build: `mvn clean package`
2. Run: `java -jar target/task-tracker-0.0.1-SNAPSHOT.jar` or `mvn spring-boot:run`

### Todos

-   [ ] Add exact Maven/Gradle commands and wrapper instructions
-   [ ] Document Java version, ports, and profiles

---

## API Endpoints

Each endpoint below includes a short description, example payload (where applicable), and todos.

### Create (POST /api/tasks)

Behavior: Create a task. Validate title (not blank, max 120), status ∊ {TODO, IN_PROGRESS, DONE}, priority ∊ {LOW, MEDIUM, HIGH}, dueDate ≥ today. Return 201 with Location header.

Example payload:

```json
{
	"title": "Buy groceries",
	"description": "Milk, bread",
	"status": "TODO",
	"priority": "MEDIUM",
	"dueDate": "2025-11-10"
}
```

Todos

-   [ ] Implement DTOs (TaskRequest) and validation annotations
-   [ ] Implement controller POST endpoint with @Valid
-   [ ] Return 201 with Location header and created resource

### Read (GET /api/tasks/{id})

Behavior: Return a task by id or 404 if not found.

Todos

-   [ ] Implement controller GET mapping and TaskService#getById
-   [ ] Add tests for 200 and 404 cases

### Update (PUT /api/tasks/{id})

Behavior: Full update with validation; updatedAt should be set automatically.

Todos

-   [ ] Implement PUT endpoint and service update logic
-   [ ] Ensure auditing updates updatedAt

### Patch status (PATCH /api/tasks/{id}/status)

Behavior: Update only the status enum.

Todos

-   [ ] Implement PATCH endpoint to update only status
-   [ ] Add validation and tests for unsupported values

### Delete (DELETE /api/tasks/{id})

Behavior: Delete by id and return 204 No Content.

Todos

-   [ ] Implement delete endpoint
-   [ ] Decide between hard delete and soft delete (stretch goal)

### List (GET /api/tasks)

Behavior: Return a pageable list with optional filters and sorting.

Query params

-   status, priority, dueBefore, dueAfter, titleContains
-   sort (e.g. dueDate,createdAt), direction (asc|desc)
-   page, size (defaults: page=0, size=20)

Todos

-   [ ] Implement Specification-based filtering (or Criteria API)
-   [ ] Support sorting and pagination with sensible defaults
-   [ ] Add integration tests for combinations of filters + sort + page

---

## Data model

-   Task entity: id (UUID|Long), title, description, status, priority, dueDate, createdAt, updatedAt
-   Enums: Status {TODO, IN_PROGRESS, DONE}, Priority {LOW, MEDIUM, HIGH}

Todos

-   [ ] Create JPA entity with auditing (@CreatedDate, @LastModifiedDate)
-   [ ] Choose id type (UUID vs Long) and document reasoning

---

## Persistence

Recommendation: Spring Data JPA + H2 (dev). Provide `application.yml` enabling H2 console and auto-DDL for local development.

Todos

-   [ ] Add Spring Data JPA and H2 dependencies
-   [ ] Create TaskRepository extends JpaRepository<Task, Long>
-   [ ] Add Specification or query methods for filtering

---

## Validation & error handling

Use Jakarta Bean Validation for DTOs and implement a global `@ControllerAdvice` that returns structured JSON errors: `{"timestamp","path","error","message","status"}`.

Todos

-   [ ] Add validation annotations (@NotBlank, @Size(max=120), @FutureOrPresent, @NotNull)
-   [ ] Implement GlobalExceptionHandler for validation and entity-not-found
-   [ ] Write tests asserting error payload shape

---

## Security

Protect write endpoints (POST/PUT/PATCH/DELETE) with HTTP Basic and an in-memory user `admin` with ROLE_ADMIN. Leave read endpoints open to anonymous or ROLE_USER.

Todos

-   [ ] Configure SecurityFilterChain to secure write endpoints
-   [ ] Create InMemoryUserDetailsManager with bcrypt password for admin
-   [ ] Add security tests for 401/403 behaviors

Stretch

-   [ ] Add JWT auth as a stretch goal

---

## Documentation & testing

-   Use `springdoc-openapi` to expose Swagger UI (e.g., `/swagger-ui.html`).
-   Unit tests: service layer with JUnit + Mockito.
-   Integration tests: `@SpringBootTest` + `@AutoConfigureMockMvc` for endpoints and validation/security flows.

Todos

-   [ ] Add springdoc dependency and verify UI path
-   [ ] Write unit tests for service logic
-   [ ] Write MockMvc integration tests for controllers

---

## Deliverables

-   Public Git repo with source and README
-   How to run (Maven/Gradle commands), Java version, ports, profiles
-   API examples (curl/HTTPie) for endpoints
-   Notes on design choices and defaults

Todos

-   [ ] Add a section with curl examples for each endpoint
-   [ ] Add a short run guide with commands and environment variables

---

## Step-by-step guide

High-level milestones

1. Scaffold via Spring Initializr (Web, Data JPA, H2, Validation, Security, OpenAPI).
2. Implement domain model and repository.
3. Implement service layer and DTO mappings.
4. Implement controllers with validation and response handling.
5. Add global error handling and tests.
6. Configure security and docs.

Todos

-   [ ] Create milestone checkboxes in project tracker (issues/milestones)

---

## Stretch goals

-   Soft delete with filter
-   Optimistic locking (`@Version`)
-   Spring Boot Actuator endpoints and custom health indicator
-   Dockerfile + docker-compose with Postgres profile
-   Caching GET by id with Caffeine

Todos

-   [ ] Prioritize stretch goals and map to sprints

---

## Acceptance criteria

-   [ ] All endpoints function and return correct status codes
-   [ ] Validation blocks bad inputs and returns clear error JSON
-   [ ] List endpoint supports filters, sorting, and pagination
-   [ ] Basic auth enforced for write operations
-   [ ] Swagger UI available and working
-   [ ] Tests cover happy paths and important error cases

---
