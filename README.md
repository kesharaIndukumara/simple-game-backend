# Smiley Game Platform Backend

A RESTful HTTP server built with **Spring Boot** that powers the Smiley Game platform. Players register, verify their email, and then play a number-guessing game driven by image-based questions fetched from an external API. The server tracks scores, awards points for correct answers, and exposes a leaderboard.

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Configuration](#configuration)
- [Build & Run](#build--run)
- [API Reference](#api-reference)
- [Game Logic](#game-logic)
- [Security](#security)
- [Deployment](#deployment)

---

## Features

- Player registration with email verification
- OAuth 2.0 + JWT-based authentication
- Image-based game questions via [Smiley API](https://marcconrad.com/uob/smile/api.php)
- Answer checking with real-time scoring
- Player level progression (Bronze → Platinum → Gold)
- Leaderboard (top scores)
- API rate limiting / throttling
- Caching with EhCache
- Distributed tracing with Spring Cloud Sleuth
- Email notifications via AWS SES

---

## Tech Stack

| Category         | Technology                                      |
|------------------|-------------------------------------------------|
| Language         | Java 11                                         |
| Framework        | Spring Boot 2.4.3                               |
| Security         | Spring Security, OAuth 2.0, JWT                 |
| Persistence      | Spring Data JPA, Hibernate, MySQL               |
| Build            | Maven (packaged as WAR)                         |
| Server           | Apache Tomcat                                   |
| Caching          | EhCache 3 (JSR-107)                             |
| Tracing          | Spring Cloud Sleuth                             |
| Email            | AWS SES (via JavaMail)                          |
| Cloud            | AWS SDK (S3, CloudWatch Logs)                   |
| Utilities        | Lombok, ModelMapper, Apache Commons, Gson       |

---

## Project Structure

```
src/main/java/com/navishkadarshana/smileygame/
├── config/
│   ├── security/          # OAuth2, JWT, security filters
│   └── throttling_config/ # Rate-limiting AOP aspect
├── constants/             # App-wide constants and email HTML templates
├── controller/
│   ├── AppController/     # Health / app info endpoints
│   └── PlayerController/  # Player & game REST endpoints
├── dto/                   # Request / response DTOs
├── entity/                # JPA entities (Player, Score, ScoreDetail)
├── enums/                 # Enumerations (Level, ActiveStatus, …)
├── exception/             # Custom exceptions and global handler
├── repository/            # Spring Data JPA repositories
├── service/               # Business logic interfaces and implementations
└── utilities/             # Email sender, token generator, SmileyAPI client
```

---

## Prerequisites

- Java 11+
- Maven 3.6+
- MySQL 5.7+ (or 8.x)
- Apache Tomcat 9.x (for WAR deployment)
- AWS SES credentials (for email sending)

---

## Configuration

The application uses Spring Profiles. The active profile is set in `application.properties`:

```properties
spring.profiles.active=local
```

Copy and edit the appropriate profile file:

| Profile | File                              |
|---------|-----------------------------------|
| `local` | `application-local.properties`    |
| `dev`   | `application-dev.properties`      |
| `prod`  | `application-prod.properties`     |

### Minimum required settings (example: `application-local.properties`)

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/simple_game?createDatabaseIfNotExist=true
spring.datasource.username=<db-user>
spring.datasource.password=<db-password>
spring.jpa.hibernate.ddl-auto=update

# Frontend base URL (for email links)
game.frontend.base.url=http://localhost:3000

# AWS SES (email)
spring.mail.host=email-smtp.<region>.amazonaws.com
spring.mail.username=<ses-smtp-user>
spring.mail.password=<ses-smtp-password>
mail.from=<verified-sender@example.com>
spring.mail.port=587
```

> **Never commit real credentials** — use environment variables or a secrets manager in production.

---

## Build & Run

### Build the WAR

```bash
./mvnw clean package -DskipTests
```

The artifact is produced at `target/api.war`.

### Run locally with embedded Tomcat (for development)

```bash
./mvnw spring-boot:run
```

The API is available at `http://localhost:8080/api/v1/`.

---

## API Reference

All endpoints are prefixed with `/api/v1`.  
Protected endpoints require an `Authorization: Bearer <access_token>` header.

### Authentication

Obtain an access token via the standard OAuth 2.0 password grant:

```
POST /api/v1/oauth/token
Content-Type: application/x-www-form-urlencoded

grant_type=password&username=<email>&password=<password>&client_id=<client_id>&client_secret=<client_secret>
```

---

### Player Endpoints

#### Register a new player

```
POST /api/v1/player/signup
```

**Request body:**

```json
{
  "userName": "john_doe",
  "email": "john@example.com",
  "password": "SecurePass@1"
}
```

**Response:**

```json
{
  "success": true,
  "message": "sign up successfully!"
}
```

> A verification email is sent to the provided address. Rate-limited to **5 requests / 60 seconds**.

---

#### Verify account / email

```
PATCH /api/v1/player/account/verify?token=<verification_token>
```

**Response:**

```json
{
  "success": true,
  "message": "Your account has been activated successfully!"
}
```

> Rate-limited to **10 requests / 60 seconds**.

---

### Game Endpoints

All game endpoints require a valid `Authorization` header.

#### Start a game session

```
POST /api/v1/game/start
Authorization: Bearer <token>
```

**Response:**

```json
{
  "success": true,
  "message": "Game started",
  "data": {
    "score_id": 42,
    "score_details_id": 101,
    "question": "https://.../question-image.png"
  }
}
```

---

#### Check an answer

```
POST /api/v1/game/answer/check
Authorization: Bearer <token>
```

**Request body:**

```json
{
  "score_id": 42,
  "score_details_id": 101,
  "answer": 7
}
```

**Response:**

```json
{
  "success": true,
  "data": {
    "is_true": true,
    "point": 10.0,
    "score_id": 42,
    "score_details_id": 102,
    "question": "https://.../next-question-image.png"
  }
}
```

---

#### End a game session

```
POST /api/v1/game/end
Authorization: Bearer <token>
```

**Request body:**

```json
{
  "score_id": 42,
  "end_type": "MANUAL"
}
```

**Response:**

```json
{
  "success": true,
  "data": {
    "score_id": 42,
    "point": 30.0
  }
}
```

---

#### Get top scores (leaderboard)

```
GET /api/v1/game/top-score
Authorization: Bearer <token>
```

**Response:**

```json
{
  "success": true,
  "data": [
    {
      "id": 5,
      "userName": "jane_doe",
      "point": 150.0,
      "date": "2024-03-15T10:30:00",
      "level_eum": "Platinum"
    }
  ]
}
```

---

## Game Logic

| Event               | Effect                                      |
|---------------------|---------------------------------------------|
| Correct answer      | +10 points to session score; +10 to player level |
| Wrong answer        | No points awarded                           |
| Player level > 1000 | Level badge set to **Platinum**             |
| Player level > 10000| Level badge set to **Gold**                 |

Questions are fetched in real time from the [Smiley API](https://marcconrad.com/uob/smile/api.php?out=json&base64=no). Each question is an image; the player must identify the correct digit (0–9).

---

## Security

- **OAuth 2.0 Authorization Server** issues JWT access tokens.
- **Resource Server** validates tokens on every protected request.
- Passwords are stored as hashed values and validated using [Passay](https://www.passay.org/) policy rules.
- API throttling (AOP-based) prevents brute-force and abuse on sensitive endpoints.

---

## Deployment

The project is packaged as a WAR and deployed to Tomcat:

```bash
# Build
./mvnw clean package -DskipTests -Pprod

# Deploy to Tomcat
cp target/api.war /opt/tomcat/webapps/
```

Logs are written to `/opt/tomcat/logs/simple-game.log` with daily rolling and a 365-day retention policy.

---

## License

This project is proprietary. All rights reserved © Navishka Darshana.
