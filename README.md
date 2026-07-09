# 💼 Job Portal Backend

A Spring Boot REST API for a Job Portal application that enables recruiters to post and manage jobs while allowing job seekers to search, save, and apply for jobs. The application implements secure JWT-based authentication and role-based authorization using Spring Security.

---

## 🚀 Features

### 👤 Authentication & Authorization
- JWT-based Authentication
- Spring Security Integration
- Role-Based Access Control (RBAC)
- Secure REST APIs

### 👨‍💼 Recruiter Features
- Register/Login
- Create Job Posts
- Update Job Posts
- Delete Job Posts
- View All Posted Jobs
- View Applicants for Each Job
- Update Application Status
  - Pending
  - Shortlisted
  - Rejected
  - Accepted

### 👨‍🎓 Job Seeker Features
- Register/Login
- Browse Available Jobs
- Search Jobs
- Save Jobs
- Apply for Jobs
- View Applied Jobs
- Track Application Status

### 📦 Backend Features
- RESTful API Design
- CRUD Operations
- Exception Handling
- DTO-based Request/Response Handling
- Layered Architecture
- PostgreSQL Database Integration

---

# 🛠️ Tech Stack

| Category | Technologies |
|----------|--------------|
| Language | Java 21 |
| Framework | Spring Boot |
| Security | Spring Security, JWT |
| Database | PostgreSQL |
| ORM | Spring Data JPA (Hibernate) |
| Build Tool | Maven |
| API Testing | Postman |
| Version Control | Git & GitHub |

---

# 📂 Project Structure

```
src
├── controller
├── service
├── repository
├── entity
├── dto
├── security
├── config
├── exception
└── resources
```

---

# 🔐 Authentication

The application uses **JWT (JSON Web Token)** for authentication.

Workflow:

1. User Registers
2. User Logs In
3. JWT Token Generated
4. Token Sent in Authorization Header
5. Protected APIs Accessed

Example Header

```
Authorization: Bearer <JWT_TOKEN>
```

---

# 👥 User Roles

## Recruiter

- Create Job
- Update Job
- Delete Job
- View Posted Jobs
- View Applicants
- Update Application Status

---

## Job Seeker

- Search Jobs
- Save Jobs
- Apply Jobs
- View Saved Jobs
- View Applied Jobs
- Track Application Status

---

# 📡 REST APIs

## Authentication

| Method | Endpoint |
|---------|----------|
| POST | /auth/register |
| POST | /auth/login |

---

## Jobs

| Method | Endpoint | Description |
|---------|----------|-------------|
| GET | /jobs | Get All Jobs |
| GET | /jobs/{id} | Get Job by ID |
| POST | /jobs | Create Job |
| PUT | /jobs/{id} | Update Job |
| DELETE | /jobs/{id} | Delete Job |

---

## Applications

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | /applications/apply | Apply Job |
| GET | /applications | Get User Applications |
| PUT | /applications/status/{id} | Update Application Status |

---

## Saved Jobs

| Method | Endpoint |
|---------|----------|
| POST | /saved-jobs |
| GET | /saved-jobs |
| DELETE | /saved-jobs/{id} |

---

# ⚙️ Getting Started

## Clone Repository

```bash
git clone https://github.com/Avi0221/JOB-Portal-SpringBoot--Backend.git

cd JOB-Portal-SpringBoot--Backend
```

---

## Configure PostgreSQL

Update your `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/jobportal

spring.datasource.username=YOUR_USERNAME

spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
```

---

## Build Project

```bash
mvn clean install
```

---

## Run Project

```bash
mvn spring-boot:run
```

Server starts at

```
http://localhost:8080
```

---

# 🧪 API Testing

The APIs can be tested using **Postman**.

Remember to include the JWT token for protected endpoints.

---

# 📌 Future Enhancements

- Email Notifications
- Resume Upload
- Company Profiles
- Advanced Job Filters
- AI Resume Screening
- AI Job Recommendations
- Interview Scheduling
- Docker Support
- CI/CD Pipeline
- Swagger/OpenAPI Documentation

---

# 👨‍💻 Author

**Avinash Kumar**

GitHub: https://github.com/Avi0221

---

# ⭐ If you like this project

Give it a ⭐ on GitHub and feel free to contribute!
