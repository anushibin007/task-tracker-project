# Task Tracker

## Overview

Create a Task Tracker (TODO tracker) project using Spring Boot as backend and React JS as frontend. Containerize the application using Docker. Then deploy the backend to Google Cloud Run. Deploy the frontend to GitHub Pages.

---

## YouTube Playlist

[Spring Boot + React JS | Task Tracker Project Playlist](https://www.youtube.com/playlist?list=PLFsDocJcusVzPsrq1VujwvUQhVRs82OED)

---

## Milestones

| Milestone Number | Activities                                                                        | Status | Youtube Video Link                                                                                          |
| ---------------- | --------------------------------------------------------------------------------- | ------ | ----------------------------------------------------------------------------------------------------------- |
| 1                | Backend project setup (Scaffolding, Entity design, Database connection)           | ✅     | [Part 1](https://www.youtube.com/live/BT1Er3ejLqM)                                                          |
| 2                | REST endpoints creation and testing                                               | ✅     | [Part 2](https://www.youtube.com/live/VUlOtstYHJg) </br> [Part 3](https://www.youtube.com/live/dZPjxiIqHX8) |
| 3                | JUnits, Global error handling and validation                                      | ⏳     | TBD                                                                                                         |
| 4                | Frontend project setup (Scaffolding, Entity design, Navbar design, Routes design) | ⏳     | TBD                                                                                                         |
| 5                | Containerize (Dockerfiles for backend and frontend)                               | ⏳     | TBD                                                                                                         |
| 6                | Deployment (Google Cloud Run & GitHub Pages)                                      | ⏳     | TBD                                                                                                         |

---

## Data model

-   Task entity:
    -   id: Long
    -   title: String
    -   description: String
    -   status: enum
    -   priority: enum
    -   dueDate: Timestamp
    -   createdAt: Timestamp
    -   updatedAt: Timestamp
-   Enums:
    -   Status {TODO, IN_PROGRESS, DONE}
    -   Priority {LOW, MEDIUM, HIGH}

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

Behavior: Return a pageable list with sorting.

Query params

-   page, size (defaults: page=0, size=20)
-   sort (e.g. dueDate,createdAt), direction (asc|desc)

---

## Validation & error handling

Use Jakarta Bean Validation for DTOs and implement a global `@ControllerAdvice` that returns structured JSON errors: `{"timestamp","path","error","message","status"}`.

Todos

-   [ ] Add validation annotations (@NotBlank, @Size(max=120), @FutureOrPresent, @NotNull)
-   [ ] Implement GlobalExceptionHandler for validation and entity-not-found
-   [ ] Write tests asserting error payload shape

---

## Acceptance criteria

-   [ ] All endpoints function and return correct status codes
-   [ ] Validation blocks bad inputs and returns clear error JSON
-   [ ] List endpoint supports filters, sorting, and pagination

---
