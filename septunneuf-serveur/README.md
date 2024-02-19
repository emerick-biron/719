# Septunneuf Server Project

<!-- TOC -->
* [Septunneuf Server Project](#septunneuf-server-project)
  * [Introduction](#introduction)
  * [Project Structure](#project-structure)
  * [Technology Stack](#technology-stack)
  * [Setup and Deployment](#setup-and-deployment)
    * [Prerequisites](#prerequisites)
    * [Building the Services](#building-the-services)
    * [Running the Application](#running-the-application)
  * [Usage](#usage)
  * [API Documentation](#api-documentation)
<!-- TOC -->

## Introduction

The Septunneuf Server project is the backend of a browser-based game designed around a microservices architecture. It allows players to create heroes, manage eggs and incubators, interact with a shop, and more. This README details the server project's setup, build, deployment, and functionality.

## Project Structure

The project is structured into several microservices, each handling a specific part of the game's functionality:

- **Eggs Service**: Manages egg-related operations.
- **Heroes Service**: Handles hero creation and management.
- **Incubators Service**: Manages incubator operations.
- **Inventory Service**: Keeps track of player inventories.
- **Monsters Service**: Manages monster-related functionalities.
- **Shop Service**: Handles operations related to the in-game shop.
- **Storage Service**: Responsible for data persistence and storage.

## Technology Stack

- **Spring Boot**: Used for creating the microservices, leveraging various starters for web support, data access (MongoDB, Redis), and messaging (RabbitMQ).
- **Kotlin and Java**: Programming languages used across different services.
- **Docker**: For containerizing the services and ensuring consistent environments.
- **MongoDB**: The primary database for services requiring document based database.
- **Redis**: Used by the shop service for services requiring key/value based database.
- **RabbitMQ**: For asynchronous messaging between services.

## Setup and Deployment

### Prerequisites

- Docker and Docker Compose installed on your machine.
- JDK 17 or later for building Java-based services.
- Kotlin for building Kotlin-based services.

### Building the Services

Each microservice can be built individually using Gradle. Navigate to the service directory and run:

```bash
./gradlew build
```

### Running the Application

Three Docker Compose files are provided for different deployment scenarios:

- `docker-compose.yml`: Standard production deployment.
- `docker-compose-dev.yml`: Development environment with additional containers for debugging and development. _Contains dozzle for log viewing and konga as ui for kong._
- `docker-compose-nocors.yml`: Deployment with Access-Control-Allow-Origin *

To deploy the services, use the following command in the directory containing the Docker Compose files:

```bash
docker-compose -f <compose-file-name> up -d
```

## Usage

All microservices running behind kong api gateway. The url of this gateway is [http://localhost:8000](http://localhost:8000).

The bases url for each microservice are as follows :

- **Eggs Service**: [http://localhost:8000/eggs](http://localhost:8000/eggs)
- **Heroes Service**: [http://localhost:8000/heroes](http://localhost:8000/heroes)
- **Incubators Service**: [http://localhost:8000/incubators](http://localhost:8000/incubators)
- **Inventory Service**: [http://localhost:8000/inventory](http://localhost:8000/inventory)
- **Monsters Service**: [http://localhost:8000/monsters](http://localhost:8000/monsters)
- **Shop Service**: [http://localhost:8000/shop](http://localhost:8000/shop)
- **Storage Service**: [http://localhost:8000/storage](http://localhost:8000/storage)

## API Documentation

Refer to each microservice's API documentation for detailed information on the endpoints and their usage. The documentation is typically available at `<service-base-url>/swagger-ui/index.html` when the service is running.