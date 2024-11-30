# Plateforme de Gestion d'Événements

A comprehensive event management platform built with **Spring Boot**, **Thymeleaf**, and **Stripe** for seamless event organization and online ticket purchases.

---

## Features

- **User Roles**: 
  - Participants can view and register for events.
  - Organizers can create, edit, and delete events.
- **Event Management**: 
  - Add events with details like title, date, capacity, and price.
  - Manage participants for each event.
- **Online Payments**:
  - Secure payment integration with **Stripe** for ticket purchases.
- **Email Verification**:
  - New users receive a confirmation email to activate their accounts.
- **RESTful APIs**: 
  - Endpoints for managing events, users, and roles.
- **Responsive UI**:
  - User-friendly interface built with **Bootstrap** and Thymeleaf.

---

## Technologies Used

| Technology        | Description                     |
|-------------------|---------------------------------|
| **Spring Boot**   | Backend framework for REST APIs and business logic. |
| **Thymeleaf**     | Template engine for dynamic views. |
| **Stripe API**    | Payment gateway for online transactions. |
| **MySQL**         | Relational database for data storage. |
| **Bootstrap**     | Frontend framework for responsive design. |
| **Java**          | Programming language for application logic. |

---

## Getting Started

### Prerequisites
- Java 11 or higher
- Maven
- MySQL
- Stripe account for payment integration
- SMTP email server credentials for email verification

---

### Configuration

1. **Database Configuration**:
   Add the following to your `application.properties` file:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/event_platform
   spring.datasource.username=root
   spring.datasource.password=your_password
   spring.mail.username=your_email
   spring.mail.password=your_password
