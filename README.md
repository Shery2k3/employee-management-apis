# Employee Management System

A REST API built with Java and Spring Boot for managing core human resources and administrative operations. The system
consolidates personnel tracking, attendance, leave management, asset assignment, and document storage into a single
unified backend.

## Overview

Organizations rely on consistent, auditable records for their people and resources. This application addresses that need
by providing a structured backend that enforces role based access control across all operations, ensuring that sensitive
data is only accessible to authorized personnel.

The codebase is written entirely in Java 17 using the Spring Framework, with Spring Data JPA handling all persistence
logic. Maven manages the build lifecycle and dependency resolution.

## Features

**Personnel Management**
Register new employees, maintain their personal and departmental records, and query the full organizational structure
through a clean REST interface.

**Attendance Tracking**
Log daily entry and exit timestamps per employee and derive working hours from those records for payroll or compliance
purposes.

**Leave Management**
Employees submit time off requests through the API. Managers review and approve or reject those requests, and the system
automatically adjusts each employee's leave balance accordingly.

**Asset Assignment**
Track company owned equipment such as laptops and mobile devices. Each asset record captures its current condition, the
employee it is assigned to, and its full assignment history.

**Document Storage**
Upload and retrieve employee documents such as contracts or identification records, stored securely and scoped to the
relevant employee.

**Authentication**
All protected endpoints require a valid access token. Tokens are issued on login and must accompany subsequent requests.

## Database

The application uses H2 as its database engine. H2 is a lightweight, Java native relational database that runs inside
the application process, removing the need for a separately installed database server during development or testing.

By default, in memory databases are discarded when the application shuts down. This application sidesteps that
limitation by configuring H2 in embedded file mode. Rather than storing data purely in RAM, H2 writes its data to a file
on disk at a path specified in the application properties. The result is a database that behaves like any other
relational engine from the application's perspective, starts up instantly with no external dependencies, and retains all
data across restarts.

To configure this, the `spring.datasource.url` property in `src/main/resources/application.properties` should follow
this pattern:

```
spring.datasource.url=jdbc:h2:file:./data/emsdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```

This tells H2 to persist all data to a file named `emsdb` in a `data` directory relative to the project root. The
`ddl-auto=update` setting ensures the schema evolves alongside any entity changes without wiping existing records.

The H2 web console can be enabled during development for direct SQL inspection:

```
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

Once running, the console is accessible at `http://localhost:8080/h2-console`.

## Running the Application

Java 17 or higher must be installed. From the project root, build the application using the included Maven wrapper:

```bash
./mvnw clean install
```

Then start the application:

```bash
./mvnw spring-boot:run
```

The server starts on port 8080 by default. All configurable values including the database path, server port, and token
secrets are managed through `src/main/resources/application.properties`.

## API Reference

### Authentication

| Method | Endpoint          | Description                                      |
|--------|-------------------|--------------------------------------------------|
| POST   | `/api/auth/login` | Authenticates a user and returns an access token |

### Employees

| Method | Endpoint         | Description                                |
|--------|------------------|--------------------------------------------|
| GET    | `/api/employees` | Returns a list of all registered employees |
| POST   | `/api/employees` | Creates a new employee record              |

### Assets

| Method | Endpoint      | Description                                             |
|--------|---------------|---------------------------------------------------------|
| GET    | `/api/assets` | Lists all assets with their current status and assignee |
| POST   | `/api/assets` | Registers a new asset in the inventory                  |

### Leave Requests

| Method | Endpoint                   | Description                                  |
|--------|----------------------------|----------------------------------------------|
| POST   | `/api/leaves`              | Submits a new leave request                  |
| PUT    | `/api/leaves/{id}/approve` | Approves the leave request with the given ID |

## Technology Stack

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* H2 Database (file mode)
* Maven