# BookMyShow Clone - Backend API

A high-performance, secure RESTful API 
built with Spring Boot to manage
the end-to-end lifecycle of
movie ticket bookings, theater 
schedules, and user authentication.


## Table of Contents
1. [Project Overview](#-project-overview)
2. [Technology Stack](#-technology-stack)
3. [System Features](#-system-features)
4. [Database Schema](#-database-architecture)
5. [API Documentation](#-api-documentation)
6. [How to Run](#-how-to-run)


## Project Overview
The **BookMyShow Backend** is 
designed to handle the core logic 
of a cinema reservation system. 
Unlike a simple CRUD application, 
this system manages complex 
relationships between entities 
and ensures a secure transaction flow.

**Key Problems Solved:**
* **Secure Access:** Only registered users can book tickets, and only Admins can modify the theater catalog.
* **Seat Management:** Prevents double-booking by tracking seat status (Available/Booked) across specific shows.
* **Relational Integrity:** Ensures that shows are linked to valid theaters and movies, preventing data mismatch.
* **Standardized Responses:** Uses a global system to ensure every API call returns a predictable JSON structure, even when errors occur.

## 🛠️ Technology Stack

This project is built using a modern,
industry-standard stack for Java 
backend development, focusing on 
scalability and security.

### Core Frameworks & Language
* **Java:** The primary programming language used for building business logic.
* **Spring Boot:** The core framework used to manage dependencies, application configuration, and the web server.
* **Spring Data JPA:** Used for Object-Relational Mapping (ORM) to handle MySQL database operations seamlessly.
* **REST API:** Designed a stateless architecture using REST principles to handle client-server communication via JSON.

### Security & Authentication
* **Spring Security:** Implemented for protecting endpoints and managing user authentication.
* **JSON Web Token (JWT):** Used for secure, token-based authorization across the REST endpoints.

### Database & Storage
* **MySQL:** A relational database used to store persistent data for users, movies, and bookings.

### Tools & Utilities
* **Maven:** Build and dependency management tool.
* **Lombok:** Used to reduce boilerplate code like Getters, Setters, and Constructors.
* **Hibernate Validator:** Used for server-side input validation via `@Valid` annotations.

## 🚀 System Features

This backend is designed with a focus on security, data consistency, and a professional user experience.

### 🔐 Secure Authentication & Authorization
* **JWT-Based Security:** Implemented a stateless authentication mechanism where users receive a secure token upon login, eliminating the need for server-side sessions.
* **Role-Based Access Control (RBAC):** The system distinguishes between different user types to protect sensitive operations:
    * **ADMIN:** Has full authority to manage movies, theaters, and schedules.
    * **USER:** Limited to viewing content and making personal bookings.

### 🎟️ Intelligent Booking Logic
* **Seat Availability Management:** Developed logic to track and update the status of individual seats for specific shows, preventing overlapping bookings.
* **Transactional Integrity:** Uses Spring Data JPA to ensure that bookings are saved correctly or not at all, maintaining database consistency.

### 🛠️ Robust Error Handling & Validation
* **Global Exception Handling:** Built a centralized system using `@ControllerAdvice` to catch errors (like `ResourceNotFound`) and return a standardized JSON response to the client.
* **Request Validation:** Utilized `@Valid` to verify all incoming data (like email formats or non-empty fields) before it reaches the service layer, preventing "bad data" from entering the database.

## 🏗️ Database Architecture

The system is powered by a relational MySQL database with a schema designed for high data integrity and performance. The architecture consists of **9 specialized tables** that manage everything from theater layouts to secure financial transactions.

### 🗄️ Detailed Table Breakdown
* **users:** Stores user credentials, contact information, and security roles.
* **movies:** The central catalog containing film details like titles, genres, and metadata.
* **theaters:** Represents the physical cinema buildings/franchises.
* **screens:** Manages the individual auditoriums within each theater, allowing for multiple screens per location.
* **seats:** Defines the physical seating grid (Rows/Numbers) for every screen.
* **shows:** The core scheduling table that links a **Movie** to a **Screen** at a specific time slot.
* **shows_seats:** A high-concurrency mapping table that tracks the real-time availability (e.g., Booked vs. Available) for every seat in a specific show.
* **bookings:** Records the final reservation details, linking users to their chosen shows.
* **payments:** Tracks the transactional status of every booking to ensure financial consistency.

### 🛡️ Data Design Principles
* **Normalization:** The schema is fully normalized to prevent data redundancy and ensure that updates (like changing a movie title) reflect instantly across all shows.
* **Relational Mapping:** Implemented complex `@OneToMany` and `@ManyToOne` relationships to ensure that a booking cannot exist without a valid user, show, and payment record.
* **Concurrency Control:** The `shows_seats` table acts as the source of truth, preventing "Double Booking" errors during high-traffic periods.

## 📂 API Documentation

The BookMyShow API is organized around REST principles. All request and response bodies are formatted in JSON. Protected endpoints require a valid JWT token in the `Authorization` header.

### 🔑 1. Authentication (`/api/auth`)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Register a new user account |
| `POST` | `/api/auth/login` | Authenticate user and receive a JWT token |

### 🎬 2. Movies (`/api/movies`)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/movies` | Get all movies |
| `GET` | `/api/movies/{id}` | Get details of a specific movie |
| `GET` | `/api/movies/search?title=...` | Search movies by title |
| `GET` | `/api/movies/language/{language}` | Filter movies by language |
| `GET` | `/api/movies/genre/{genre}` | Filter movies by genre |
| `POST` | `/api/movies` | **(Admin Only)** Add a new movie |
| `PUT` | `/api/movies/{id}` | Update movie details |
| `DELETE` | `/api/movies/{id}` | Remove a movie from the catalog |

### 🏢 3. Theaters & Screens (`/api/theaters`, `/api/screens`)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/theaters` | List all theaters |
| `GET` | `/api/theaters/{id}` | Get theater details by ID |
| `GET` | `/api/theaters/city/{city}` | Find theaters in a specific city |
| `POST` | `/api/theaters` | **(Admin Only)** Register a new theater |
| `POST` | `/api/screens/theater/{theaterId}` | Add a screen to a theater |
| `GET` | `/api/screens/theater/{theaterId}` | List all screens in a theater |

### 📅 4. Shows & Seat Availability (`/api/shows`, `/api/show-seats`)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/shows` | List all scheduled shows |
| `GET` | `/api/shows/{id}` | Get show details by ID |
| `GET` | `/api/shows/movie/{movieId}` | Get all timings for a specific movie |
| `GET` | `/api/shows/search?movieId=...&city=...` | Find shows by movie and city |
| `GET` | `/api/shows/date?startDate=...&endDate=...` | Filter shows by date/time range |
| `GET` | `/api/show-seats/show/{showId}` | **Real-time Seat Map:** Get available seats for a show |
| `POST` | `/api/shows` | Create a new show schedule |

### 🎫 5. Bookings & Payments (`/api/bookings`, `/api/payments`)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/bookings` | Create a reservation (Validates via `@Valid`) |
| `GET` | `/api/bookings/{id}` | Get booking details by ID |
| `GET` | `/api/bookings/number/{bookingNumber}` | Find booking by unique number |
| `GET` | `/api/bookings/user/{userId}` | Fetch a user's booking history |
| `DELETE` | `/api/bookings/{id}` | Cancel an existing booking |
| `POST` | `/api/payments` | Process payment for a booking |
| `GET` | `/api/payments/transaction/{transactionId}` | Verify payment via transaction ID |

### 👤 6. User Management (`/api/users`)
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/users` | **(Admin Only)** List all registered users |
| `GET` | `/api/users/{id}` | Get user profile by ID |
| `PUT` | `/api/users/{id}` | Update user information |
| `DELETE` | `/api/users/{id}` | Delete a user account |

## ⚙️ How to Run

Follow these steps to set up and run the backend locally.


## 1. Prerequisites

* **Java:** Ensure you have the Java Development Kit (JDK) installed.
* **MySQL:** A running MySQL server instance.
* **Maven:** To manage dependencies and build the project.


## 2. Build and Execution

1. Open a terminal in the project root directory.

2. Build the project and download all dependencies:

```bash
mvn clean install
```

3. Start the application:

```bash
mvn spring-boot:run
```

4.The API will be accessible at http://localhost:8080.

---

## 3. Testing the API

Once the application starts successfully (default port: **8080**):

1. **Public Access:**  
   Register a user using  
   `POST /api/auth/register`

2. **Authentication:**  
   Login using  
   `POST /api/auth/login`  
   to receive your **JWT Token**

3. **Protected Access:**

    - Open **Postman**
    - Go to the **Authorization** tab
    - Select **Bearer Token** from the Type dropdown
    - Paste your **JWT Token** in the Token field
    - You can now access protected endpoints like:  
      `/api/bookings`
