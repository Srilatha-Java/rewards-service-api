# Rewards API

A Spring Boot REST API that calculates customer reward points based on transactions from the last 3 months.

---

## Features

* Calculate reward points per customer
* Monthly reward aggregation
* Total reward points calculation
* Fetch rewards for all customers
* RESTful API design
* Global exception handling
* H2 in-memory database
* Unit tests for Service, Controller, and Repository

---

## Tech Stack

* Java 17
* Spring Boot 3
* Spring Data JPA
* H2 Database
* Lombok
* JUnit 5
* Mockito

---

## Reward Calculation Logic

* 2 points for amount greater than 100
* 1 point for amount between 50 and 100
* 0 points for amount less than or equal to 50

---

## Sample Data

File: `src/main/resources/data.sql`

```sql
-- Customer (ID is auto-generated)
INSERT INTO customer (name) VALUES ('Srilatha');

-- Transactions (last 3 months)
INSERT INTO transactions (customer_id, amount, transaction_date)
VALUES (1, 120, CURRENT_DATE - 10);

INSERT INTO transactions (customer_id, amount, transaction_date)
VALUES (1, 80, CURRENT_DATE - 20);

INSERT INTO transactions (customer_id, amount, transaction_date)
VALUES (1, 60, CURRENT_DATE - 5);

INSERT INTO transactions (customer_id, amount, transaction_date)
VALUES (1, 30, CURRENT_DATE - 15);

INSERT INTO transactions (customer_id, amount, transaction_date)
VALUES (1, 180, CURRENT_DATE - 25);

INSERT INTO transactions (customer_id, amount, transaction_date)
VALUES (1, -55, CURRENT_DATE - 12);
```

---

## How to Run

```
mvn spring-boot:run
```

---

## API Endpoints

### Get rewards for a customer

```
GET /api/rewards/{customerId}
```

Example:

```
http://localhost:8080/api/rewards/1
```

### Sample Response

```json
{
  "customerId": 1,
  "monthlyPoints": {
    "MARCH": 90,
    "FEBRUARY": 650
  },
  "totalPoints": 740
}
```

---

### Get rewards for all customers

```
GET /api/rewards
```

---

### Error Case (Customer Not Found)

```
http://localhost:8080/api/rewards/999
```

### Error Response

```json
{
  "timestamp": "2026-03-27T16:38:50.2045974",
  "status": 404,
  "message": "Customer not found: 999"
}
```

---

## Author

Srilatha B
Spring Boot Developer
