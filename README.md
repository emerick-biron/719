# 719 Project

<!-- TOC -->
* [719 Project](#719-project)
  * [Authors](#authors)
  * [Introduction](#introduction)
  * [Project Structure](#project-structure)
  * [Getting Started](#getting-started)
    * [Prerequisites](#prerequisites)
    * [Running the Project](#running-the-project)
    * [Accessing the Application](#accessing-the-application)
  * [Architecture Overview](#architecture-overview)
  * [Development](#development)
    * [Microservices](#microservices)
    * [Frontend](#frontend)
    * [Deployment](#deployment)
<!-- TOC -->

## Authors

- [Emerick Biron](https://github.com/emerick-biron)
- [Louis Rocchi](https://github.com/Tchoupitoo)
- [Jagaa Michel Sainte Rose](https://github.com/JagaaIMT)

## Introduction

Welcome to the 719 Project repository, a browser-based game leveraging a microservice architecture. This game allows
players to create heroes, manage eggs, incubators, and monsters, and interact with an in-game shop. This README provides
an overview of the project's structure, setup, and how to get started with development and deployment.

## Project Structure

The project is divided into two main parts:

- **septunneuf-client**: A React application serving as the front-end interface for the game.
- **septunneuf-serveur**: Contains all backend microservices built with Spring Boot and Kotlin/Java, responsible for the
  game's core functionality.

Each part of the project has its dedicated README file providing detailed instructions and information. For
backend-specific details, refer to the [septunneuf-serveur README](./septunneuf-serveur/README.md).

## Getting Started

### Prerequisites

Before you begin, ensure you have Docker and Docker Compose installed on your machine. This is crucial for running the
services and the front-end application in containers.

### Running the Project

1. **Clone the Repository**

    Start by cloning this repository to your local machine.

2. **Build and Run the Containers**

    Navigate to the root directory of the cloned project and run the following command to start all services and the client
application:

    ```bash
    docker-compose up -d
    ```

This command will build and run the containers as defined in the docker-compose.yml file.

### Accessing the Application

Once the containers are up and running, you can access the front-end application by navigating to http://localhost:8080 in your web browser.

## Architecture Overview

The application architecture is designed around microservices, each running in its own Docker container. These services communicate with each other through RESTful APIs, RabbitMQ for messaging, and are orchestrated with Docker Compose. Kong API Gateway is used to route external requests to the appropriate service.

For a detailed explanation of the microservices architecture and the role of each service, please see the [septunneuf-serveur README](./septunneuf-serveur/README.md).

## Development

### Microservices

The backend is composed of multiple microservices, each responsible for a specific domain within the game (e.g., handling eggs, heroes, incubators). These services are built using Spring Boot and are configured to run in Docker containers.

### Frontend

The frontend is a React application that communicates with the backend services through the Kong API Gateway. It provides the user interface for players to interact with the game.

### Deployment

The provided docker-compose.yml file simplifies the deployment process, setting up the necessary services, databases, and networking. Additional compose files are available for different deployment scenarios, including development and no-CORS setups.
