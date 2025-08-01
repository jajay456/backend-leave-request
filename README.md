# APITEST
A RESTful API for generating and storing user tokens in a PostgreSQL database.  
Developed using Java 21, Spring Boot 3.3.9, JPA, and Swagger UI for API documentation.


## Features

 - [x] POST /api/insert-user – Save user data and token into database

 - [x] GET /api/get-all-user – Retrieve all users as response DTO

 - [x] POST /api/create-leave-requests – Create new leave request

 - [x] GET /api/get-all-leave-requests – Get all leave requests

 - [x] PUT /api/update-leave-status/{id} – Update leave status by ID

 - [x] GET /api/get-leave-requests-by-user/{userId} – Get leave requests by user ID

 - [x] GET /api/leave-requests/pending – Get all pending leave requests (for manager)

 - [x] GET /api/get-all-leave-type – Get all leave types

 - [x] GET /api/leave-report?month=&year=&department= – Get summarized leave details by user, month, and department (optional)

 - [x] GET /api/total-remaining-days/{userId} – Get total remaining leave days for user

 - [x] GET /api/total-leave-days/{userId} – Get total leave days used by user

 - [x] GET /api/pending-count/{userId} – Get pending leave request count by user

 - [x] GET /api/leave-balance-all-type/{userId} – Get leave balance by leave type for user
 
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
```
  ## Project Structure
|---LeaveApplication/
|      |--src/
|        |--main/
|            |--java/
|               |--com/example/apitest/
|                  |--controller/
|                     |--Controller.java
|                  |--dto/
|                     |--CreateLeaveRequestDto.java
|                     |--LeaveBalanceDto.java
|                     |--LeaveBalanceSummaryDto.java
|                     |--LeaveRequest.java
|                     |--LeaveTypeDto
|                     |--UpdateLeaveRequestStatusDto.java
|                     |--UserDTO.java
|                  |--entity/
|                     |--LeaveBalance.java
|                     |--LeaveType.java
|                     |--LeaveRequest.java
|                     |--User.java
|                  |--repository/
|                     |--LeaveBalanceRepository.java
|                     |--LeaveTypeRepository.java
|                     |--LeaveRequestRepository.java
|                     |--UserReposiory.java
|                  |--service/
|                     |--LeaveTypeService.java
|                     |--LeaveBalanceService.java
|                     |--LeaveTypeService.java
|                     |--LeaveRequestService.java
|                     |--UserService.java
|                  |--ApitestApplication.java
|            |--resources
|               |--application.properties
|   |--pom.xml
|   |--.gitignore
|   |--.gitattributes
```
## Database
 ### Table: leave_balance
| # | Column Name    | Data Type     | Not Null | Default                | Notes            |
|---|----------------|---------------|:--------:|------------------------|------------------|
| 1 | id             | `bigserial`   |    ✓     | `nextval('...')`       | Primary key      |
| 2 | user_id        | `int8`        |    ✓     | —                      | FK → users.id*   |
| 3 | leave_type_id  | `int8`        |    ✓     | —                      | FK → leave_type.id* |
| 4 | year           | `int4`        |    ✓     | —                      | Calendar year    |
| 5 | remaining_days | `numeric(...)`|    ✓     | —                      | ระบุ precision/scale ให้ตรงของจริง (เช่น `numeric(3,1)`) |

## Table: leave_request
| # | Column Name     | Data Type       | Not Null | Default          | Notes                           |
|---|-----------------|-----------------|:--------:|-----------------|--------------------------------|
| 1 | id              | `bigserial`     |    ✓     | `nextval('...')`| Primary key                     |
| 2 | user_id         | `int8`          |    ✓     | —               | FK → users.id*                  |
| 3 | leave_type_id   | `int8`          |    ✓     | —               | FK → leave_type.id*             |
| 4 | start_date      | `date`          |    ✓     | —               | วันที่เริ่มลา                   |
| 5 | end_date        | `date`          |    ✓     | —               | วันที่สิ้นสุดการลา             |
| 6 | reason          | `varchar(255)`  |          | `null`          | เหตุผลในการลา                   |
| 7 | status          | `varchar(255)`  |          | `'pending'`     | สถานะ (`pending`, `approved`, `rejected`) |
| 8 | manager_comment | `varchar(255)`  |          | `null`          | ความเห็นจากหัวหน้า             |
| 9 | created_at      | `timestamp`     |          | `CURRENT_TIMESTAMP` | เวลาที่สร้างคำขอลา           |

## Table: leave_type
| # | Column Name | Data Type      | Not Null | Default          | Notes                 |
|---|-------------|----------------|:--------:|-----------------|-----------------------|
| 1 | id          | `bigserial`    |    ✓     | `nextval('...')`| Primary key           |
| 2 | name        | `varchar(255)` |    ✓     | —               | ชื่อประเภทการลา       |
| 3 | description | `varchar(255)` |          | `null`          | คำอธิบายเพิ่มเติม     |
| 4 | max_days    | `int4`         |          | `30`            | จำนวนวันลาสูงสุดต่อปี |

## Table: users
| # | Column Name | Data Type      | Not Null | Default            | Notes                    |
|---|-------------|----------------|:--------:|--------------------|--------------------------|
| 1 | id          | `bigserial`    |    ✓     | `nextval('...')`   | Primary key              |
| 2 | username    | `varchar(255)` |    ✓     | —                  | ชื่อผู้ใช้ (ควร unique) |
| 3 | email       | `varchar(255)` |    ✓     | —                  | อีเมล (ควร unique)       |
| 4 | role        | `varchar(255)` |    ✓     | —                  | บทบาท เช่น `USER`, `ADMIN` |
| 5 | department  | `varchar(255)` |          | `null`             | แผนก                      |
| 6 | created_at  | `timestamp`    |          | `CURRENT_TIMESTAMP`| เวลาที่สร้าง             |

## How to Run
1.Open Project in IntelliJ IDEA

2.Run Project

3.Open link Swagger [http://localhost:8080/apitest/swagger-ui.html](http://localhost:8080/swagger-ui/index.html)

4.Choose method and click try it out

5.Click Execute

6.Check Response


