# Food Delivery Microservices Setup

This document explains the backend microservices added for your task:
- Eureka Discovery Server
- API Gateway
- user-service (CRUD)
- order-service (CRUD + OpenFeign to user-service)
- RabbitMQ asynchronous events between services

## Project Paths

- `C:\Users\majda\Desktop\Eureka`
- `C:\Users\majda\Desktop\api-gateway`
- `C:\Users\majda\Desktop\user-service`
- `C:\Users\majda\Desktop\order-service`
- `C:\Users\majda\Desktop\docker-compose-rabbitmq.yml`

## Ports

- Eureka: `8761`
- API Gateway: `8080`
- user-service: `8081`
- order-service: `8082`
- RabbitMQ AMQP: `5672`
- RabbitMQ UI: `15672`

## Run Order

1. Start RabbitMQ:
   - `docker compose -f C:\Users\majda\Desktop\docker-compose-rabbitmq.yml up -d`
2. Start Eureka:
   - `cd C:\Users\majda\Desktop\Eureka`
   - `mvnw.cmd spring-boot:run`
3. Start user-service:
   - `cd C:\Users\majda\Desktop\user-service`
   - `mvnw.cmd spring-boot:run`
4. Start order-service:
   - `cd C:\Users\majda\Desktop\order-service`
   - `mvnw.cmd spring-boot:run`
5. Start API Gateway:
   - `cd C:\Users\majda\Desktop\api-gateway`
   - `mvnw.cmd spring-boot:run`

## Eureka Dashboard

Open: `http://localhost:8761`

You should see:
- `API-GATEWAY`
- `USER-SERVICE`
- `ORDER-SERVICE`

## CRUD Test via Gateway

Base URL: `http://localhost:8080`

### 1) Create User
POST `/api/users`
```json
{
  "fullName": "Majda User",
  "email": "majda@example.com",
  "phone": "22334455",
  "address": "Tunis"
}
```

### 2) Create Order (Feign checks user existence)
POST `/api/orders`
```json
{
  "userId": 1,
  "restaurantName": "Pizza House",
  "status": "PENDING",
  "totalPrice": 34.5
}
```

### 3) Read
- GET `/api/users`
- GET `/api/orders`

### 4) Update
- PUT `/api/users/{id}`
- PUT `/api/orders/{id}`

### 5) Delete
- DELETE `/api/users/{id}`
- DELETE `/api/orders/{id}`

## Integration Implemented

- OpenFeign:
  - `order-service` calls `user-service` before creating/updating an order.
- RabbitMQ:
  - `order-service` publishes `order.created` and `order.updated` events.
  - `user-service` consumes `order.*` events.
  - `user-service` publishes `user.created` events.
  - `order-service` consumes `user.*` events.

## Note about local Maven build

If Maven dependency downloads fail with certificate/PKIX errors on your machine, configure your Java truststore/corporate certificate and try again.
