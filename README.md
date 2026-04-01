# Employee Management System

Welcome to the Employee Management REST API project. This application serves as a comprehensive backend backend solution for managing daily human resources and administrative operations. Built completely with Java and the Spring Framework, it provides a stable foundation for organizational management.

## Project Overview

This system centralizes all essential company processes into one unified platform. It provides specialized services to track personnel, organize them into specific departments, and monitor their daily attendance. Additionally, it takes care of leave applications, secures document storage, and handles company property assignments. The application enforces role based access control to ensure that sensitive data remains secure and is only accessible by authorized personnel.

## Core Capabilities

* Personnel Management: Register new staff members, update their personal details, and view the entire organizational structure.
* Attendance Tracking: Accurately record daily entry and exit times to calculate working hours.
* Time Off Processing: Staff members can submit applications for time off. Managers can subsequently review, approve, or reject these applications. The system automatically updates the available balances for each person.
* Company Property Handling: Allocate laptops, mobile devices, and other equipment to individuals. The system tracks the condition and assignment history of every item.
* Document Storage: Securely upload, store, and retrieve important files such as employment contracts or identification cards.
* Secure Access: Protected endpoints that enforce strict authentication using reliable token mechanisms.

## How to Run

To run this application locally, you will need Java installed on your computer. 

Open your terminal and navigate to the root directory of the project. You can use the Maven wrapper included in the project folder to compile the source code. Simply run the Maven install command to download dependencies and build the application. Once the build finishes successfully, you can launch the application by running the generated executable file or by starting the Main class directly from your integrated development environment. 

The application will start on the default web server port. You can configure your database connection and other environmental variables inside the application properties file located in the resources folder.

## Example API Routes

Once the application is running, you can interact with various endpoints. Below are a few examples of how to communicate with the application.

### Authentication
* Endpoint: POST /api/auth/login
* Purpose: Authenticates a user with their credentials and provides an access token for further requests.

### Employees
* Endpoint: GET /api/employees
* Purpose: Retrieves a complete list of all registered staff members.

* Endpoint: POST /api/employees
* Purpose: Adds a new person to the system. You must provide their basic details such as name, email address, and their assigned department identifier in the request body.

### Company Assets
* Endpoint: GET /api/assets
* Purpose: Lists all equipment along with their current condition and the individual currently holding them.

* Endpoint: POST /api/assets
* Purpose: Registers a new piece of equipment into the company inventory.

### Time Off
* Endpoint: POST /api/leaves
* Purpose: Submits a new application for days off covering a specific date range.

* Endpoint: PUT /api/leaves/2/approve
* Purpose: Approves the specific time off request associated with the identifier of 2.

## Technology Stack

* Java 17
* Spring Web Framework
* Spring Data JPA
* Relational Database Engine
* Maven Dependency Management
