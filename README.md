# APITEST
A RESTful API for generating and storing user tokens in a PostgreSQL database.  
Developed using Java 21, Spring Boot 3.3.9, JPA, and Swagger UI for API documentation.


## Features

 POST /api/insert-user – Save user data and token into database

 GET /api/get-all-user – Retrieve all users as response DTO

 POST /api/create-leave-requests – Create new leave request

 GET /api/get-all-leave-requests – Get all leave requests

 PUT /api/update-leave-status/{id} – Update leave status by ID

 GET /api/get-leave-requests-by-user/{userId} – Get leave requests by user ID

 GET /api/leave-requests/pending – Get all pending leave requests (for manager)

 GET /api/get-all-leave-type – Get all leave types

 GET /api/leave-report?month=&year=&department= – Get summarized leave details by user, month, and department (optional)

 GET /api/total-remaining-days/{userId} – Get total remaining leave days for user

 GET /api/total-leave-days/{userId} – Get total leave days used by user

 GET /api/pending-count/{userId} – Get pending leave request count by user

 GET /api/leave-balance-all-type/{userId} – Get leave balance by leave type for user
 
 Swagger UI for testing and documentation
 
 Integrated with PostgreSQL 15

## Technologies Used
  - Java 21
  - Spring Boot 3.3.9
  - Spring Web
  - Spring Data JPA
  - PostgreSQL
  - springdoc-openapi (Swagger)
  - Maven

## Installing
  - install Oracle JDK 21
  - install Apache Maven 3.9.11
  - install Postgres 15 for used Database
  - install IntelliJ IDEA for Run Code
