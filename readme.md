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

## 🔗 Project Repository

You can find the full implementation of the homework assignment (Spring Cloud – Dynamic Configuration Refresh) in the following repository:

👉 **[Spring Cloud Config Dynamic Refresh – GitHub Repository](https://github.com/gkowalczyk/Microservices_Architecture-Course/blob/main/m2/src/main/java/com/example/m2/Config.java)**

The repository contains:
- `Config Server` configured with Git integration,
- `Eureka Server` for service discovery,
- `m2` microservice using `@RefreshScope` and `ContextRefresher`,
- Implementation of the **scheduler-based configuration refresh mechanism**,
- Sample logs demonstrating automatic updates without restarting the service.

## 🧱 Module 4 — Designing and Building Complementary Services

### 🔹 Introduction
Module 4 builds on the previous Config Server and Service Discovery topics by
creating complementary microservices that work together to implement one business
use case. In this repository, the example services are `customer-ms` and
`order-ms`.

### 🔹 Module scope
Topics covered in this module:

- **Introduction** — overview of the module goal and microservice application context.
- **Defining microservices** — splitting responsibilities between services.
- **Client-Side Load Balancing** — choosing a service instance on the client side.
- **Round-robin** — a basic strategy for distributing traffic between instances.
- **Overriding default Service Discovery configuration** — adapting communication
  with services registered in Eureka.
- **Server-side load balancer** — comparison with an approach where traffic is
  distributed by a component placed in front of services.
- **Microservice responsibility** — each service owns a clear and focused scope.
- **Adding a database to a microservice** — a separate database and migrations for
  a selected service.
- **Creating configuration dedicated to a specific microservice** — separate
  profiles and properties for `customer-ms` and `order-ms`.
- **Homework and module materials** — practical task and PDF notes.

### 🔹 Module assignment
The goal of the assignment is to create a microservice application with simple
business logic and configuration for both development and production profiles.

In this repository, the assignment is implemented as the Customer Order application:

1. `customer-ms` stores customer data.
2. `order-ms` creates customer orders.
3. Before creating an order, `order-ms` calls `customer-ms` to verify that the
   customer exists.
4. Both services use PostgreSQL and Flyway.
5. Both services can load configuration from the Config Server.
6. Services register in Eureka and communicate by application name.

### 🔹 Application architecture

```text
                  +----------------+
                  | Eureka Server  |
                  |    :8761       |
                  +--------+-------+
                           ^
                           |
        +------------------+------------------+
        |                                     |
+-------+--------+                    +-------+--------+
|  customer-ms   | <--- HTTP/REST ---- |    order-ms    |
| random port    |                    | random port    |
+-------+--------+                    +-------+--------+
        |                                     |
        v                                     v
+----------------+                    +----------------+
| customer DB    |                    | order DB       |
| Flyway         |                    | Flyway         |
+----------------+                    +----------------+

                  +----------------+
                  | Config Server  |
                  |    :8889       |
                  +----------------+
```

### 🔹 Application modules

#### Eureka Server
Eureka Server acts as the service registry. Microservices register with Eureka,
so other services can find them by `spring.application.name` without hardcoding a
specific host and port.

#### Config Server
Config Server provides microservice configuration from a Git repository. In
`customer-ms` and `order-ms`, the Config Server import is optional, so both
applications can also start with local `dev` profile settings.

#### customer-ms
`customer-ms` is responsible for customer data.

Key elements:
- JPA entity `Customer`,
- repository `CustomerRepo`,
- endpoint `GET /customers/{customerId}`,
- Flyway migration that creates the `customers` table,
- PostgreSQL and Flyway configuration.

Endpoint used by `order-ms`:

```http
GET /customers/{customerId}
```

Responses:
- `200 OK` — the customer exists,
- `404 NOT_FOUND` — the customer does not exist.

#### order-ms
`order-ms` is responsible for customer orders.

Key elements:
- JPA entity `OrderEntity`,
- repository `OrderRepo`,
- business logic in `OrderService`,
- endpoint `POST /orders`,
- endpoint `GET /orders/customer/{customerId}`,
- Flyway migration that creates the `customer_orders` table,
- HTTP client `CustomerClient` that calls `customer-ms`.

Create an order:

```http
POST /orders
Content-Type: application/json

{
  "customerId": 1,
  "productName": "Course access",
  "quantity": 1
}
```

List orders for a customer:

```http
GET /orders/customer/1
```

### 🔹 Client-Side Load Balancing in the application
`order-ms` calls `customer-ms` through the Eureka service name:

```text
http://customer-ms/customers/{customerId}
```

Spring Cloud LoadBalancer chooses one of the available `customer-ms` instances.
This means `order-ms` does not need to know the port of a specific instance,
which is important because services can run on random ports (`server.port=0`).

In `order-ms`, the HTTP client configuration separates:
- a regular `RestClient.Builder` for infrastructure clients, including Eureka,
- a dedicated `@LoadBalanced RestClient.Builder` only for calls to `customer-ms`.

This prevents Eureka from treating `localhost` from `http://localhost:8761/eureka`
as a service id.

### 🔹 Development and production profiles
The assignment requires configuration for development and production environments.

The repository contains the development profile configuration:
- `customer-ms/src/main/resources/application-dev.properties`,
- `order-ms/src/main/resources/application-dev.properties`.

For the production profile, analogous configuration files should be prepared,
for example in the Config Server repository:
- `customer-ms-prod.properties`,
- `order-ms-prod.properties`.

Typical differences between profiles:

| Area | Dev profile | Prod profile |
|------|-------------|--------------|
| Database | local or test PostgreSQL database | production PostgreSQL database |
| Passwords | environment variables or test values | secrets/environment variables only |
| Service port | often `server.port=0` | random port or platform-managed port |
| SQL logging | can be enabled | usually disabled |
| Flyway | enabled | enabled |
| Eureka | local Eureka | production Eureka URL |

### 🔹 Database migrations
Each microservice owns its data model and its own Flyway migrations.

`customer-ms`:

```text
customer-ms/src/main/resources/db/migration/V1__create_tables.sql
```

Creates the table:

```text
customers
```

`order-ms`:

```text
order-ms/src/main/resources/db/migration/V1__create_tables.sql
```

Creates the table:

```text
customer_orders
```

### 🔹 Local startup order
Recommended local startup order:

1. PostgreSQL,
2. Eureka Server,
3. Config Server,
4. `customer-ms`,
5. `order-ms`.

The Config Server import is optional, so if it is not running, services can still
start with their local `application-dev.properties` configuration.

### 🔹 Summary
Module 4 shows how to move from individual Spring Cloud building blocks to a
small cooperating microservice application. `customer-ms` and `order-ms` have
separate responsibilities, separate databases, their own Flyway migrations,
environment-specific configuration, and communication through Eureka with
client-side load balancing.

## 🧱 Module 5 — Synchronous Communication and Circuit Breaker


## 🔗 Project Repository

The complete implementation of the module assignment is available in the course repository:

👉 **[Module 5 — Synchronous Communication and Circuit Breaker](https://github.com/gkowalczyk/Microservices_Architecture-Course/tree/main/post-client-ms)**

The project contains:

- a synchronous declarative HTTP client,
- integration with an external API,
- Resilience4j Circuit Breaker,
- fallback handling,
- storage of the last successful response,
- Spring Cache or Caffeine configuration,
- unit tests,
- integration tests,
- logs demonstrating fallback behaviour when the external service is unavailable.

---

## 🔹 Summary

Module 5 explains how microservices communicate synchronously using HTTP and how to make this communication more resilient.

The key concepts are:

- imperative clients explicitly build HTTP requests,
- declarative clients describe remote APIs as Java interfaces,
- unit tests verify isolated application logic,
- integration tests verify HTTP communication,
- Circuit Breaker prevents repeated calls to a failing service,
- fallback methods provide alternative responses,
- cache stores the last known successful response,
- the client can continue returning useful data even when the remote service is temporarily unavailable.


---

## 📨 Module 6 — Asynchronous Communication with Apache Kafka

### 🔹 Introduction

This module presents an event-driven microservices architecture built with
**Apache Kafka**. The application collects CPU and RAM metrics, processes them
asynchronously, generates alerts, and exposes the results for visualization in
**Grafana**.

### 🔹 Module scope

Topics covered in this module:

- analysis of the project structure and its key components,
- configuration of Apache Kafka and Kafdrop,
- modelling microservices responsible for acquiring system metrics,
- stream processing with a Kafka consumer,
- designing a microservice that collects generated alerts,
- event factory usage,
- integration of Apache Kafka with Grafana,
- visualization and monitoring of the data flow.

### 🔹 Data flow

1. `cpu-ram-metrics-collector-ms1` collects CPU and RAM usage data.
2. The producer publishes system metrics to an Apache Kafka topic.
3. The analyzer consumes the metrics and evaluates the configured thresholds.
4. When a threshold is exceeded, the analyzer publishes an alert event.
5. The alert collector consumes and stores the generated alerts.
6. Kafdrop makes it possible to inspect Kafka topics and messages.
7. Grafana presents the collected data in a monitoring dashboard.

### 🔹 System architecture

👉 [Open the full-size microservices architecture diagram](https://github.com/gkowalczyk/Microservices_Architecture-Course/blob/main/cpu-ram-metrics-collector-ms1/src/main/resources/monitoring-kafka-architecture.jpg)

![Kafka-based system monitoring architecture](https://github.com/gkowalczyk/Microservices_Architecture-Course/raw/main/cpu-ram-metrics-collector-ms1/src/main/resources/monitoring-kafka-architecture.jpg)

### 🔹 Grafana visualization

👉 [Open the full-size Grafana dashboard](https://github.com/gkowalczyk/Microservices_Architecture-Course/blob/main/cpu-ram-metrics-collector-ms1/src/main/resources/grafana.png)

![Grafana dashboard for system metrics and alerts](https://github.com/gkowalczyk/Microservices_Architecture-Course/raw/main/cpu-ram-metrics-collector-ms1/src/main/resources/grafana.png)

### 🔗 Project repository

The complete implementation is available in the course repository:

👉 **[Module 6 — Kafka System Metrics Monitoring](https://github.com/gkowalczyk/Microservices_Architecture-Course/tree/main/cpu-ram-metrics-collector-ms1)**

### 🔹 Summary

Module 6 demonstrates how Apache Kafka enables asynchronous communication
between independent microservices.

The key concepts are:

- producers publish system metrics as events,
- consumers process messages independently,
- Kafka topics separate metrics from alerts,
- Kafdrop enables topic and message inspection,
- alert events are generated when configured thresholds are exceeded,
- Grafana provides visualization and operational monitoring.

---

## 🐇 Alternative Implementation — Asynchronous Communication with RabbitMQ

### 🔹 Introduction

As an extension of the Kafka-based system, the same microservice workflow was
implemented with **RabbitMQ** and **Spring AMQP**. This variant demonstrates how
the messaging layer can be replaced without changing the core responsibilities
of the microservices.

The system collects CPU and RAM metrics, analyzes memory usage, generates alert
events when configured thresholds are exceeded, and exposes the collected alerts
through a REST endpoint for visualization in Grafana.

### 🔹 RabbitMQ messaging model

The implementation uses:

- `DirectExchange` for routing messages,
- durable queues for storing metrics and alerts,
- routing keys for connecting exchanges with the appropriate queues,
- `RabbitTemplate` for publishing Java objects as JSON,
- `@RabbitListener` for asynchronous message consumption,
- `JacksonJsonMessageConverter` for Java–JSON conversion.

The messaging topology consists of:

| Message | Exchange | Routing key | Queue |
|---|---|---|---|
| `SystemMetrics` | `metrics.exchange` | `system.metrics` | `system-metrics.queue` |
| `RamAlert` | `alerts.exchange` | `ram.alert` | `alerts.queue` |

### 🔹 Data flow

1. `cpu-ram-metrics-collector-ms1-rabbitMQ` collects CPU and RAM metrics.
2. The collector publishes `SystemMetrics` to `metrics.exchange`.
3. RabbitMQ routes the message to `system-metrics.queue`.
4. `data-aggregator-analyzer-rabbitmq` consumes and analyzes the metrics.
5. When RAM usage exceeds a configured threshold, the analyzer creates a `RamAlert`.
6. The analyzer publishes the alert to `alerts.exchange`.
7. RabbitMQ routes the alert to `alerts.queue`.
8. `alerts-dispatcher-rabbitMq` consumes the alert and exposes it through `GET /get-alerts`.
9. Grafana retrieves the alerts from the REST endpoint and visualizes them.

### 🔹 System architecture

👉 [Open the full-size RabbitMQ architecture diagram](https://github.com/gkowalczyk/Microservices_Architecture-Course/blob/main/cpu-ram-metrics-collector-ms1-rabbitMQ/monitoring-rabbitmq-architecture.jpg)

![RabbitMQ-based system monitoring architecture](https://github.com/gkowalczyk/Microservices_Architecture-Course/raw/main/cpu-ram-metrics-collector-ms1-rabbitMQ/monitoring-rabbitmq-architecture.jpg)

### 🔗 RabbitMQ microservices

- **[CPU/RAM Metrics Collector](https://github.com/gkowalczyk/Microservices_Architecture-Course/tree/main/cpu-ram-metrics-collector-ms1-rabbitMQ)** — collects system metrics and publishes `SystemMetrics`.
- **[Data Aggregator Analyzer](https://github.com/gkowalczyk/Microservices_Architecture-Course/tree/main/data-aggregator-analyzer-rabbitmq)** — consumes metrics, analyzes RAM usage, and publishes `RamAlert`.
- **[Alerts Dispatcher](https://github.com/gkowalczyk/Microservices_Architecture-Course/tree/main/alerts-dispatcher-rabbitMq)** — consumes alerts and exposes them through a REST API.

### 🔹 Kafka and RabbitMQ variants

Both implementations provide the same business flow but use different messaging
models:

- Kafka stores events in topics and tracks consumer progress with offsets.
- RabbitMQ routes messages through exchanges to queues and removes them after
  successful consumer acknowledgement.
- The microservices remain loosely coupled in both variants.

### 🔹 Summary

The RabbitMQ implementation demonstrates:

- replacing Kafka with another messaging broker,
- exchange, queue, binding, and routing-key configuration,
- asynchronous communication with Spring AMQP,
- JSON serialization and deserialization,
- automatic message acknowledgement after successful processing,
- separation between metric collection, analysis, and alert presentation.
