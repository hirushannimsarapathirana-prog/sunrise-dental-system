# 🦷 Sunrise Dental Clinic Management System

A web-based **Dental Clinic Management System** developed to simplify and organize daily clinic operations, including appointment management, patient-related workflows, billing, validation, and appointment searching.

The system is built using **Java, MySQL, HTML, CSS, and JavaScript**, with a Java HTTP Server used for backend API handling.

---

## 📌 Project Overview

The **Sunrise Dental Clinic Management System** is designed for authorized clinic staff to manage dental clinic activities through a centralized system.

The application provides functionality for managing appointments, searching appointment records, updating and deleting appointments, handling billing, and validating user input.

The project follows a structured backend approach with Java services and database interaction while providing a web-based frontend.

---

## ✨ Features

### 🔐 Authentication

* User login
* Authentication validation
* Authorized staff access

### 📅 Appointment Management

* Create appointments
* View appointments
* Search appointments
* Update appointment information
* Delete appointments
* Validate appointment data

### 💳 Billing

* Manage billing information
* Process clinic-related billing data
* Validate billing information

### 🔎 Search

* Search appointment records
* Retrieve relevant appointment information

### ✅ Validation

* Input validation
* Required-field validation
* Data consistency checks

---

## 🏗️ System Architecture

```text
┌─────────────────────────────────────────┐
│              FRONTEND                   │
│                                         │
│        HTML / CSS / JavaScript          │
└────────────────────┬────────────────────┘
                     │
                     │ HTTP Requests
                     ▼
┌─────────────────────────────────────────┐
│               BACKEND                   │
│                                         │
│          Java HTTP Server               │
│                                         │
│  Controllers / Services / DAO Logic     │
└────────────────────┬────────────────────┘
                     │
                     │ SQL Queries
                     ▼
┌─────────────────────────────────────────┐
│              DATABASE                   │
│                                         │
│                 MySQL                   │
└─────────────────────────────────────────┘
```

---

## 🛠️ Technologies Used

| Technology       | Purpose                           |
| ---------------- | --------------------------------- |
| ☕ Java           | Backend development               |
| 📦 Maven         | Project and dependency management |
| 🗄️ MySQL        | Database management               |
| 🔄 Gson          | JSON processing                   |
| 🌐 HTML5         | Frontend structure                |
| 🎨 CSS3          | Frontend styling                  |
| ⚡ JavaScript     | Frontend functionality            |
| 🧪 Postman       | API testing                       |
| 🔧 Git           | Version control                   |
| 🐙 GitHub        | Source code management            |
| 💻 IntelliJ IDEA | Development environment           |

---

## 📂 Project Structure

```text
sunrise-dental-system/
│
├── Frontend/
│   ├── HTML files
│   ├── CSS files
│   └── JavaScript files
│
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── sunrise/
│                   └── dental/
│
├── .gitignore
├── pom.xml
└── README.md
```

---

## 🔄 Application Flow

```text
User
 │
 ▼
Login
 │
 ▼
Dental Clinic System
 │
 ├── Appointment Management
 │     ├── Add
 │     ├── View
 │     ├── Search
 │     ├── Update
 │     └── Delete
 │
 ├── Billing
 │
 └── Validation
        │
        ▼
      MySQL
```

---

## 📅 Appointment Management

The appointment module allows authorized staff to manage clinic appointments.

### Supported Operations

```text
CREATE
   ↓
VIEW
   ↓
SEARCH
   ↓
UPDATE
   ↓
DELETE
```

This allows clinic staff to maintain appointment information efficiently.

---

## 🗄️ Database

The system uses **MySQL** as its relational database.

The database layer stores and manages information required by the clinic management system.

### Database Responsibilities

* Store appointment information
* Retrieve appointment records
* Update appointment data
* Delete appointment records
* Support search operations
* Store billing-related information

---

## 🔌 Backend

The backend is implemented using a **Java HTTP Server**.

The backend is responsible for:

* Receiving HTTP requests
* Processing application logic
* Validating input
* Communicating with MySQL
* Returning JSON responses
* Managing appointment operations

JSON serialization and deserialization are handled using **Gson**.

---

## 🧪 API Testing

The backend APIs can be tested using **Postman**.

Typical API operations include:

```text
POST    → Create
GET     → Retrieve
PUT     → Update
DELETE  → Delete
```

Postman is used to verify API requests, responses, validation, and backend functionality.

---

## ⚙️ Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/hirushannimsarapathirana-prog/sunrise-dental-system.git
```

### 2. Open the Project

Open the project using **IntelliJ IDEA**.

Make sure the project is imported as a Maven project.

### 3. Configure MySQL

Create the required MySQL database and configure the database connection in the project.

Update the database configuration with your local MySQL credentials.

```text
Database URL
Username
Password
```

> Do not commit real database passwords or other sensitive credentials to GitHub.

### 4. Build the Project

```bash
mvn clean install
```

### 5. Run the Backend

Run the Java HTTP Server from IntelliJ IDEA.

The backend can then be accessed through the configured HTTP port.

### 6. Open the Frontend

Open the files inside the `Frontend` directory using a local web server such as **Live Server**.

---

## 🧑‍💻 Development Tools

This project was developed using:

* IntelliJ IDEA
* MySQL
* MySQL Workbench
* Maven
* Postman
* Git
* GitHub
* VS Code / Live Server

---

## 🎯 Project Objectives

The main objectives of this project are:

* Develop a real-world clinic management application
* Implement backend APIs using Java
* Work with relational databases
* Implement CRUD operations
* Practice client-server communication
* Process JSON data
* Implement input validation
* Test REST-style API operations
* Apply software engineering concepts

---

## 🔮 Future Improvements

Possible future improvements include:

* 👤 Patient management
* 👨‍⚕️ Dentist management
* 📅 Advanced appointment scheduling
* 💊 Treatment management
* 💰 Complete payment management
* 📊 Dashboard and reports
* 🔐 Improved authentication and authorization
* 🔑 JWT-based authentication
* 📱 Responsive mobile interface
* 📖 API documentation with Swagger/OpenAPI
* 🧪 Automated unit and integration testing
* ☁️ Cloud deployment

---

## 📸 Screenshots

Screenshots of the application can be added here.

```text
Frontend Screenshot
Backend/API Screenshot
Appointment Management Screenshot
Database Screenshot
```

---

## 📚 Learning Outcomes

Through this project, I gained practical experience with:

* Java backend development
* HTTP server development
* CRUD operations
* MySQL database integration
* JSON processing with Gson
* Frontend and backend communication
* API development
* Postman API testing
* Maven dependency management
* Git and GitHub
* Software engineering project structure

---

## 👨‍💻 Author

### Hirushan Nimsara Pathirana

Software Development / Computer Science Student

GitHub:
https://github.com/hirushannimsarapathirana-prog

---

## 📄 License

This project was developed for **educational and academic purposes**.

---

## ⭐ Repository

If you find this project useful, feel free to ⭐ the repository.

**GitHub Repository:**
https://github.com/hirushannimsarapathirana-prog/sunrise-dental-system
