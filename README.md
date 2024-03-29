# Job Application Microservices with Spring Boot

## Overview
This project is a microservices architecture built using Spring Boot, where each microservice is responsible for managing specific functionalities related to jobs, companies, and reviews.

## Microservices
- **Job Microservice**: Manages job listings including job title, description, requirements, etc.
- **Company Microservice**: Handles company details such as name, industry, location, etc.
- **Review Microservice**: Manages reviews about companies including ratings, comments, etc.

## Technologies Used
- **Spring Boot**: For building the microservices.
- **Spring Data JPA**: For data persistence and manipulation.
- **PostgreSQL**: As the relational database management system for storing microservice data.
- **Spring Boot Actuator**: For monitoring and managing the microservices in production environments.
- **RESTful APIs**: For communication between microservices.
- **OpenFeign**: For declarative REST client communication between microservices.
- **Zipkin**: For distributed tracing of microservice requests.
- **Spring Cloud Config Server**: For centralized configuration management.
- **Git Repository**: For storing configuration files used by the Spring Cloud Config Server.
- **Spring Cloud Gateway**: For API gateway and routing.
- **Resilience4J**: For resilience and fault tolerance.
- **RabbitMQ**: For asynchronous messaging and event-driven architecture.
- **Docker/Kubernetes**: For containerization and orchestration of microservices using Kubernetes.
- **JUnit/Mockito**: For unit testing and mocking dependencies.
- **Postman**: For testing the APIs.

## Setup Instructions
1. Follow the setup instructions provided for each microservice in their respective directories.
2. Set up Spring Cloud Config Server and configure it to use your Git repository for centralized configuration management.
3. Set up Spring Cloud Gateway for API gateway and routing.
4. Integrate Resilience4J with Spring Boot for resilience and fault tolerance.
5. Integrate RabbitMQ with Spring Boot for asynchronous messaging and event-driven architecture.
6. Set up Kubernetes to orchestrate the microservices.

## Usage
- Each microservice is deployed and managed using Kubernetes.
- Interact with the microservices through their RESTful APIs.
- Use Postman to test the APIs.

