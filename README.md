# Employee Management REST APIs

A Spring Boot backend for HR and administration workflows including employee records, attendance, leave, departments,
assets, users, and employee document handling.

## Overview

The project is built with Java 21 and Spring Boot. It uses PostgreSQL for persistence, Flyway for schema versioning,
Redis for caching, and AWS S3 for document file storage. API contracts are documented through OpenAPI/Swagger.


## Tech Stack

- Java 21
- Spring Boot 4.0.3
- Spring Web MVC
- Spring Data JPA
- Spring Security
- Bean Validation
- PostgreSQL
- Flyway
- Redis + Spring Cache
- AWS SDK v2 (S3)
- springdoc-openapi (Swagger UI)
- Maven

## Project Highlights

- DTO-first API layer organized by domain packages (`auth`, `employee`, `department`, `asset`, `leave`, `attendance`,
  `document`, `common`).
- Paginated responses via a shared `PagedResponse<T>` DTO.
- Department reads cached in Redis with cache eviction on writes.
- Documents validated (size/type), sanitized, stored in S3, and linked in DB.
- Flyway migration file initializes core HR schema.

## Prerequisites

- Java 21+
- PostgreSQL running locally (default: `localhost:5432`)
- Redis running locally (default: `localhost:6379`)
- AWS credentials available to the default AWS provider chain (for S3 operations)

## Configuration

Main config is in `src/main/resources/application.yml` and profile overrides in
`src/main/resources/application-local.yml`.

Important properties:

```yaml
spring:
  datasource:
    url: ${DB_URL:jdbc:postgresql://localhost:5432/employee_management_db}
    username: ${DB_USERNAME:employee_app_user}
    password: ${DB_PASSWORD}
  flyway:
    enabled: true

aws:
  s3:
    bucket-name: employee-management-spring
    region: us-east-1
```

Redis defaults are configured in `application-local.yml`:

```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
```

## Database and Migrations

- Primary database: PostgreSQL
- Migration tool: Flyway
- Initial schema script: `src/main/resources/db/migration/V1__Initial_Schema.sql`
- JPA mode: `ddl-auto: validate`

Create the database before first run:

```sql
CREATE DATABASE employee_management_db;
```

Flyway applies migrations automatically at startup.

## Run Locally

Build:

```bash
./mvnw clean install
```

Run:

```bash
./mvnw spring-boot:run
```

On Windows PowerShell, you can also use:

```powershell
.\mvnw.cmd clean install
.\mvnw.cmd spring-boot:run
```

Default app URL: `http://localhost:8080`

## API Documentation

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Alternate UI path: `http://localhost:8080/swagger-ui/index.html`

## API Reference

### Auth

| Method | Endpoint           | Description |
|--------|--------------------|-------------|
| POST   | `/api/auth/signup` | Register user |
| POST   | `/api/auth/login`  | Login (currently returns user payload, not JWT) |

### Employee

| Method | Endpoint                               | Description |
|--------|----------------------------------------|-------------|
| GET    | `/api/employee/?page=0&size=10`        | List employees (paginated) |
| POST   | `/api/employee/onboard`                | Onboard employee |
| PATCH  | `/api/employee/{id}/assign-department` | Assign/clear department |
| PATCH  | `/api/employee/{id}/assign-manager`    | Assign/clear manager |
| GET    | `/api/employee/{id}/subordinates?page=0&size=10` | List subordinates |

### Department

| Method | Endpoint                                        | Description |
|--------|-------------------------------------------------|-------------|
| POST   | `/api/department/`                              | Create department |
| GET    | `/api/department/?page=0&size=10`               | List departments (cached, paginated) |
| GET    | `/api/department/{id}/employees?page=0&size=10` | List department employees |

### Assets

| Method | Endpoint                  | Description |
|--------|---------------------------|-------------|
| POST   | `/api/assets`             | Create asset |
| GET    | `/api/assets?page=0&size=10` | List assets (paginated) |
| GET    | `/api/assets/{id}`        | Get asset by id |
| PUT    | `/api/assets/{id}`        | Update asset |
| DELETE | `/api/assets/{id}`        | Delete asset |

### Leave

| Method | Endpoint                           | Description |
|--------|------------------------------------|-------------|
| POST   | `/api/leave/submit`                | Submit leave request |
| PATCH  | `/api/leave/{id}/review`           | Approve/reject leave request |
| GET    | `/api/leave/calendar?page=0&size=10` | List leave calendar entries |

### Attendance

| Method | Endpoint                   | Description |
|--------|----------------------------|-------------|
| POST   | `/api/attendance/check-in` | Check in |
| POST   | `/api/attendance/check-out`| Check out |

### Document

| Method | Endpoint                                         | Description |
|--------|--------------------------------------------------|-------------|
| POST   | `/api/document/upload`                           | Upload employee document (multipart) |
| GET    | `/api/document/employee/{employeeId}?page=0&size=10` | List employee documents |
| GET    | `/api/document/{id}`                             | Get document metadata |
| PUT    | `/api/document/{id}`                             | Update file and/or doc type |
| DELETE | `/api/document/{id}`                             | Delete document and S3 object |

### User

| Method | Endpoint                  | Description |
|--------|---------------------------|-------------|
| GET    | `/api/user/?page=0&size=10` | List users (paginated) |

## Document Upload Constraints

- Max file size: 10 MB
- Allowed types:
  - PDF (`application/pdf`)
  - JPEG (`image/jpeg`, `image/jpg`)
  - PNG (`image/png`)
  - DOC (`application/msword`)
  - DOCX (`application/vnd.openxmlformats-officedocument.wordprocessingml.document`)

## Caching

- Caching is enabled globally (`@EnableCaching`).
- Department list and single department reads are cached in Redis.
- Department create operation evicts related cache entries.
- Cache TTL is set to 60 minutes.
