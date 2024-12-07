# 🛠️ Service Marketplace Backend API

This is a backend REST API application for a service marketplace built with **Spring Boot**. The application allows vendors to list their services, users to book or hire those services, and admins to approve or reject services listed by vendors. The API uses **Spring Data JPA**, **Hibernate**, **Spring Security**, **JWT Authentication**, **Role-based Authorization**, and **MySQL** as the database. The API is documented using **Swagger** and is thoroughly tested using **JUnit** and **Mockito**.

## 🚀 Features

## Features
- **Vendor** Role:  
  - Add new services
  - View & Update their own listed services
- **User** Role:  
  - Book or hire services offered by vendors
  - Update their own profile
- **Admin** Role:  
  - Approve or reject services added by vendors
  - Manage vendors and users
- **Authentication**:  
  - JWT-based authentication
  - Role-based authorization
- **Database**:  
  - MySQL as the database engine
- **API Documentation**:  
  - Swagger UI for API documentation and testing
- **Logging**:  
  - Application logging for tracking and debugging
- **Exception Handling**:  
  - Global exception handler for consistent error responses
- **Testing**:  
  - Unit testing with **JUnit** and **Mockito**

## 🛠 Technologies Used

- **Backend Framework:** Spring Boot
- **Database:** MySQL, H2
- **ORM:** Hibernate, Spring Data JPA
- **Security:** Spring Security, JWT Authentication, Role-based Authorization
- **API Documentation:** Swagger
- **Testing:** JUnit, Mockito
- **Logging:** SLF4J & Dedicated log file for easy analysis 
- **Exception Handling:** Global Exception Handler

## 🏁 Getting Started

### 📋 Prerequisites

- Java 11 or higher
- Maven
- MySQL

### 📦 Installation

1. **Clone the repository:**
    ```sh
    git clone https://github.com/your-username/service-marketplace-backend.git
    ```
2. **Navigate to the project directory:**
    ```sh
    cd service-marketplace-backend
    ```
3. **Set up the MySQL database:**
    - Create a new database named `marketplace`.
    - Update the `application.properties` file with your MySQL database credentials.
4. **Build the project:**
    ```sh
    mvn clean install
    ```
 5. **Test the application:**
    ```sh
    mvn test
    ```   
6. **Run the application:**
    ```sh
    mvn spring-boot:run
    ```


## 📄 Swagger API Documentation

You can access the Swagger API documentation at: 
```sh
http://localhost:8080/swagger-ui.html
```

## 📊 Logging

Logging is configured using the built-in SLF4J logging support. You can check the logs in the `logs` directory.

## 🙏 Acknowledgments

Special thanks to the open-source community for providing amazing libraries and tools.

## 🖼️ Swagger-UI Images
![Image 1](images/swagger1.png)  

![Image 2](images/swagger2.png)  

![Image 3](images/swagger3.png)  

![Image 4](images/swagger4.png)

