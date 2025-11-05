#  Spring Cloud — Microservices and Distributed Architecture


---

## 🧩 Module 2 — Philosophy of Microservices and Distributed Architecture

### 🔹 Introduction
An overview of how software architecture evolved from **monoliths** to **microservices**, explaining why distributed systems became essential in modern development.

### 🔹 Birth of Microservices
Explains the transition from tightly coupled monolithic systems to small, independently deployable services.  
Highlights the need for scalability, flexibility, and faster development cycles.

### 🔹 What is a Microservice?
A **microservice** is a small, autonomous unit of business functionality that:
- runs in its own process,
- communicates over lightweight protocols (usually HTTP/REST),
- can be deployed, scaled, and updated independently.

### 🔹 When NOT to Use Microservices
Discusses the drawbacks and challenges:
- Increased complexity of deployment and monitoring,
- Higher network latency and configuration overhead,
- Difficulties in maintaining data consistency across services.

Key takeaway:
> Microservices make sense only when scalability and modularity justify the added complexity.

### 🔹 Types of Architectures
Compares several architectural styles:
- **Monolithic Architecture** – single deployable unit.
- **Layered Architecture** – separated presentation, business, and data layers.
- **Service-Oriented Architecture (SOA)** – the precursor to microservices.
- **Microservices Architecture** – distributed, independently deployable components.
- **Event-Driven Architecture** – uses asynchronous communication via message brokers.

### 🔹 Architectural Patterns
Introduces patterns commonly used in microservices:
- API Gateway Pattern
- Database per Service
- Service Discovery
- Circuit Breaker Pattern
- Configuration Server Pattern

### 🔹 Summary
Microservices architecture is not a trend — it’s an **engineering philosophy**.  
It focuses on autonomy, scalability, resilience, and faster innovation through independently managed services.

---

## 🧭 Module 3 — Config Server and Service Discovery

### 🔹 Introduction
The module introduces **Spring Cloud Config Server** and **Eureka Server** — two foundational components for managing configuration and service discovery in microservice environments.

### 🔹 Service Registration and Discovery
Describes how microservices automatically register themselves in **Eureka Server** and discover other services dynamically.  
This eliminates the need to hardcode IP addresses or URLs.

**Key Concepts:**
- `@EnableEurekaServer` – starts a discovery registry.
- `@EnableEurekaClient` – registers a service with the registry.
- Each service identifies itself with `spring.application.name`.

### 🔹 Implementing Eureka Server
Covers step-by-step setup of a discovery server using **Spring Cloud Netflix Eureka**:
1. Add the dependency `spring-cloud-starter-netflix-eureka-server`.
2. Enable the server with `@EnableEurekaServer`.
3. Configure the registry endpoint on port `8761`.
4. Observe registered microservices via the Eureka dashboard.

### 🔹 RestClient vs RestTemplate
Comparison between the different HTTP clients in Spring:
| Client | Type | Description |
|--------|------|--------------|
| **RestTemplate** | Synchronous | Classic blocking client (deprecated). |
| **RestClient** | Synchronous | Modern replacement with fluent API (Spring 6+). |
| **WebClient** | Reactive | Non-blocking, asynchronous client for WebFlux apps. |

## 🧠 Homework — Spring Cloud: Practicing Knowledge Through Application

### 🎯 Objective
The purpose of this assignment is to **develop creative thinking and practical skills** in the context of **microservices and Spring Cloud**.  
The goal is to apply the mechanisms discussed in the module in a **creative, problem-oriented** way to design a practical solution.

---

### 🧩 Task Description
> Your task is to create a solution that allows **dynamic updating of application configuration** fetched from a Git repository **without rebuilding or restarting the application**.

The standard **Spring Cloud Config Server** does not support this functionality **natively**, so your goal is to design and implement a **custom mechanism** that enables dynamic configuration refresh.

---

### 🧭 Suggested Approaches

You may consider one or more of the following methods:

1. **Webhook-based approach**  
   Use Git webhooks to automatically notify the application when configuration changes occur.

2. **Spring Boot Actuator endpoint**  
   Trigger configuration refresh manually via a dedicated endpoint such as `/actuator/refresh`.

3. **Scheduler-based polling (recommended)**  
   Implement a scheduler that periodically (e.g. every 15 minutes) checks the repository or Config Server for updates and reloads the configuration dynamically.

---


