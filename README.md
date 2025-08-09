#  Expense Tracker Microservices

# Overview
This project is a microservices-based Expense Tracker application built with Spring Boot.
It allows users to manage expenses, categories, finances, and sends notifications, with authentication & service discovery.

# Microservices Architecture
The system is divided into 6 main services:

1. API Gateway
Purpose: Entry point for all client requests.
Responsibilities:
Routes requests to respective microservices.
Applies centralized authentication & authorization checks.
Tech: Spring Cloud Gateway.

2. Eureka Server
Purpose: Service discovery.
Responsibilities:
Registers all microservices dynamically.
Allows services to find and communicate with each other without hardcoding URLs.
Tech: Spring Cloud Netflix Eureka.

3. Auth User Service
Purpose: Handles user authentication & registration.
Responsibilities:
User sign-up and login.
JWT generation & validation.
Role-based access control.
Tech: Spring Boot Security, JWT, MySQL.

4. Expense Category Service
Purpose: Manages expense categories.
Responsibilities:
CRUD operations for categories.
Category ownership per user.
Tech: Spring Boot, MySQL, JPA.

5. Finance Service
Purpose: Handles financial transactions & expense storage.
Responsibilities:
CRUD for expenses.
Validates ownership.
Sends notification events to Kafka after expense creation.
Tech: Spring Boot, MySQL, JPA, Kafka Producer.

6. Notification Service
Purpose: Sends notifications (e.g., email, console logs, etc.) when new expenses are created.
Responsibilities:
Consumes messages from Kafka.
Triggers notification sending logic.
Tech: Spring Boot, Kafka Consumer.

⚙ Tech Stack
Backend: Java 17, Spring Boot
Microservices: Spring Cloud (Eureka, Gateway)
Database: SQL Server
Security: Spring Security, JWT
Messaging: Apache Kafka

Build Tool: Maven
API Testing: Postman

#Workflow
User Registration & Login
User registers in Auth User Service.
Logs in → JWT token generated.
API Gateway checks token for every request.

Expense Creation
User sends request → API Gateway → Finance Service.
Finance Service validates & stores expense.
Sends notification event to Kafka topic.
Notification Trigger
Notification Service listens to Kafka.
Sends notification (email/log).

Category Management
CRUD operations via Expense Category Service.
Service Discovery
All services register with Eureka Server.
Gateway discovers them dynamically.

