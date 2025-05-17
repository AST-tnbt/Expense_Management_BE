# Expense Management System - Backend

A scalable, microservices-based backend for an expense management mobile application that helps users track, manage, and analyze personal expenses.  
**The project is carried out for educational purposes, so it is not yet complete for commercialization**

## Overview

This system is built using a microservices architecture, providing a robust and scalable solution for expense management. Each service is containerized using Docker and can be deployed independently or together using Docker Compose.

*Figma design is taken from: [Expense management app ui kit](https://www.figma.com/community/file/1341354400491602502)* 

## Technology Stack

- **Backend Framework**: Spring Boot
- **Database**: MySQL 8.0
- **Caching**: Redis 7.0
- **Containerization**: Docker & Docker Compose
- **API Gateway**: Spring Cloud Gateway
- **Authentication**: JWT (JSON Web Tokens)

## Architecture

The system consists of the following microservices:

### 1. API Gateway
- Routes API requests to appropriate services
- Handles request validation and authentication
- Provides a unified entry point for all client requests

### 2. Auth Service
- Manages user registration and authentication
- Handles JWT token generation and validation
- Provides user management functionality

### 3. Expense Service
- Manages expense entries and categories
- Provides CRUD operations for expense tracking
- Supports categorization of expenses

### 4. Analysis Service (Planned)
- Analyzes expense data
- Provides insights and reports
- Generates statistics and visualizations

## ERD
ERD of the project will be updated later.

## Use-case model
Use-case model of the project will be updated later.

## Installation


### Installation and Setup

1. Clone the repository:
   ```
   git clone https://github.com/AST-tnbt/Expense_Management_BE.git
   ```
2. Build Docker Image for each service:
    ```
    cd <service-directory>
    docker build -t <name-of-image-in-docker-compose-file> .
    ```
3. Start the services using Docker Compose:
   ```
   docker-compose up -d
   ## or docker compose up -d (if using Docker Desktop)
   ```

4. The services will be available at:
   - API Gateway: http://localhost:8000
   - Other services should not be exposed ports


## API Endpoints

### Auth Service
- `/auth/**` - Authentication endpoints
- `/users/**` - User management endpoints
- `/logout` - Logout endpoint
- `/refresh` - Token refresh endpoint

### Expense Service
- `/categories/**` - Expense category endpoints
- `/expenses/**` - Expense management endpoints

## Environment Configuration

The system uses environment variables for configuration, which are set in the docker-compose.yml file. You can modify these variables to suit your needs.

## Development

### Building Services Individually

Each service can be built and run independently. Navigate to the service directory and use Maven to build:

```
./mvnw clean package
```

### Adding New Services

To add a new service to the architecture:

1. Create a new Spring Boot application
2. Add it to the docker-compose.yml file
3. Configure the API Gateway to route requests to the new service

## Security

- JWT authentication for secure API access
- Separate databases for different concerns
- Redis for secure token storage


