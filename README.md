# Notes Service
- A simple RESTful service for managing notes and authors built with Spring Boot, Spring Data JPA, and H2 database.
## Project Overview
- Create, retrieve, and delete authors and notes
- Built with Java 17+, Spring Boot, Spring Data JPA, and H2 in-memory database
- Input validation using Jakarta Validation
- API documented through Swagger UI (OpenAPI)
- Unit and integration tests using JUnit 5 and Mockito
## API Endpoints
- `POST /authors` — Create a new author
- `GET /authors` — Get all authors
- `GET /authors/{id}` — Get author by ID
- `POST /notes` — Create a new note
- `GET /notes` — Get all notes
- `GET /notes/{id}` — Get note by ID
- `DELETE /notes/{id}` — Delete note by ID
## Tests
1. Unit Tests
- Test individual service methods in isolation using Mockito.
- Validate business logic, input handling, creation, retrieval, and error scenarios to ensure services behave correctly.

2. Integration Tests
- Verify REST endpoints using MockMvc, including JSON serialization/deserialization and input validation.
- Cover typical workflows such as creating, retrieving, and deleting authors and notes, including handling error cases and HTTP status codes.
---
## Requirements
- Java 17 or later
- Maven 3.8 or later
