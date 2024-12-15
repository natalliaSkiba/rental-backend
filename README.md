# Rental Backend Project

## Project Overview

The Rental Backend Project is a Java-based RESTful API designed to manage rental properties. It supports user registration, authentication, and the management of rental listings. This project is built with Spring Boot and integrates JWT-based authentication for secure access.

## Features

- **User Management:**

    - Registration of new users.
    - Authentication with JWT.
    - Retrieval of user details.

- **Rental Management:**

    - Adding new rental properties.
    - Viewing rental details.
    - Storing and serving rental property images.

## What You Will Learn in This Project

After gaining expertise in front-end development with Angular, this project will help you strengthen your back-end development skills with Java and Spring, essential for building robust services and full-stack software solutions.

In this project, you will create a platform connecting tenants and landlords by implementing an API REST, database interaction, and an authentication system.

You will learn to:

- Use **Java** and **Spring Boot** for back-end development.
- Integrate **Spring Data** for data management.
- Implement security configurations with **Spring Security**.
- Document your API with **Swagger**.

**Key Requirement:**
Users (tenants or landlords) must authenticate to access rental listings. The API must also be documented with Swagger for usability.

## Technologies Used

- **Backend Framework:** Spring Boot
- **Database:** MySQL
- **Authentication:** JWT (JSON Web Tokens)
- **File Storage:** Local file system
- **Build Tool:** Maven

## Setup Instructions

### Prerequisites

1. Install Java 11 or higher.
2. Install Maven.
3. Install MySQL and create a database for the project.

### Configuration

1. Clone the repository:

   ```bash
   git clone https://github.com/natalliaSkiba/rental-backend.git
   cd rental-backend
   ```

2. Update the `application.yml` file with your database credentials:

   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/rental_project_db
       username: your_username
       password: your_password
   ```

3. Set up file storage for rental images in the `application.yml` file:

   ```yaml
   file:
     upload-dir: src/main/resources/static/uploads/
     base-url: http://localhost:3001/uploads/
   ```

### Running the Application

1. Build the project using Maven:
   ```bash
   mvn clean install
   ```
2. Run the application:
   ```bash
   mvn spring-boot:run
   ```
3. The application will be accessible at `http://localhost:3001`.

### API Documentation

- The API documentation is available at:
  ```
  http://localhost:3001/swagger-ui/
  ```

## Project Structure

```
📁 src/
├── 📂 main/
│   ├── 📂 java/
│   │   └── 📂 com.openclassrooms.rental_backend/
│   │       ├── 📂 config/              # Spring Security and application configurations
│   │       ├── 📂 controller/          # REST API controllers
│   │       ├── 📂 DTO/                 # Data Transfer Objects
│   │       ├── 📂 entity/              # JPA entities
│   │       ├── 📂 exception/           # Custom exception handling
│   │       ├── 📂 repository/          # Data repositories
│   │       ├── 📂 service/             # Business logic and services
│   │       └── 📄 RentalBackendApplication.java  # Main Spring Boot application
│   ├── 📂 resources/
│       ├── 📂 static/uploads/          # Directory for storing uploaded images
│       ├── 📂 templates/               # Placeholder for templates (if applicable)
│       └── 📄 application.yml          # Application configuration
├── 📂 test/
│   └── 📂 java/                        # Unit and integration tests
├── 📂 target/                          # Compiled bytecode and packaged application
├── 📄 .gitignore                       # Git ignore file
├── 📄 HELP.md                          # Maven's default help file
└── 📄 mvnw                             # Maven wrapper for builds
```

## Key Endpoints

### API Endpoints Overview

| **⚡ Méthode HTTP** | **🔹 Route**            | **📚 Description**                               | **📊 Réponse attendue**       | **🔒 Code de réponse** |
|---------------------------|--------------------------------|-----------------------------------------------|----------------------------------|----------------------|
| POST                     | /api/auth/register            | Enregistrement d'un nouvel utilisateur        | Jeton JWT                        | 200, 400             |
| POST                     | /api/auth/login               | Authentification de l'utilisateur             | Jeton JWT                        | 200, 401             |
| GET                      | /api/auth/me                  | Récupération des informations de l'utilisateur actuel | Données de l'utilisateur         | 200, 401             |
| POST                     | /api/messages                 | Envoi d'un message au propriétaire            | Message de confirmation          | 200, 400, 401        |
| GET                      | /api/rentals                  | Récupération de la liste des biens en location  | Liste des biens                  | 200, 401             |
| GET                      | /api/rentals/{id}             | Récupération des informations d'un bien    | Données du bien                  | 200, 401             |
| POST                     | /api/rentals                  | Création d'un nouveau bien en location        | Message de confirmation          | 200, 401             |
| PUT                      | /api/rentals/{id}             | Mise à jour des informations d'un bien        | Message de confirmation          | 200, 401             |
| GET                      | /api/user/{id}                | Récupération des informations d'un utilisateur | Données de l'utilisateur         | 200, 401             |

## Reference Documentation
For further reference, please consider the following sections:

- [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
- [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.5/maven-plugin)
- [Create an OCI image](https://docs.spring.io/spring-boot/3.3.5/maven-plugin/build-image.html)
- [Spring Web](https://docs.spring.io/spring-boot/3.3.5/reference/web/servlet.html)
- [Spring Security](https://docs.spring.io/spring-boot/3.3.5/reference/web/spring-security.html)
- [OAuth2 Client](https://docs.spring.io/spring-boot/3.3.5/reference/web/spring-security.html#web.security.oauth2.client)
- [Spring Data JPA](https://docs.spring.io/spring-boot/3.3.5/reference/data/sql.html#data.sql.jpa-and-spring-data)
- [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.3.5/reference/using/devtools.html)

## Guides
The following guides illustrate how to use some features concretely:

- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
- [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
- [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
- [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
- [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
- [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
- [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)



