
# TEST  ORDER  API'S
This project implements a robust Order Management System with key functionalities for managing Invoices, Inventory, and Order Placement. The application integrates services for validation, PDF generation, and API-based communication.




## Features

1 . Order Placement:
Place orders with full transactional integrity, ensuring inventory validation and invoice generation.

Inventory Management:
Validates product availability during the order process.
Ensures stock consistency across transactions.
Invoice Management:

Saves invoice data securely.
Generates PDF invoices using the iText PDF library.
Provides raw, authenticated URLs to download generated invoices.
API Endpoints:


Secure Communication:
APIs are secured and return authenticated URLs for sensitive resources.




---

### Endpoint Handlers

| **Endpoint**                | **Method** | **Handler Class**    | **Handler Method**                                  | **Consumes** | **Produces** |
|-----------------------------|------------|-----------------------|----------------------------------------------------|--------------|--------------|
| `/user/hi`                  | `GET`      | `UserController`      | `getMethodName(String, HttpServletResponse)`      | None         | None         |
| `/orders/{order_id}/invoice`| `GET`      | `OrderController`     | `getMethodName(String)`                           | None         | None         |
| `/user/login`               | `POST`     | `UserController`      | `postMethodName(UserDTO, HttpServletResponse)`    | None         | None         |
| `/user/login`               | `GET`      | `UserController`      | `Login(String, String, HttpServletResponse)`      | None         | None         |
| `/user/refresh`             | `POST`     | `UserController`      | `Refresh_token(HttpServletRequest)`              | None         | None         |
| `/orders/place`             | `POST`     | `OrderController`     | `postMethodName(Map, HttpServletRequest)`         | None         | None         |
| `/test/{order_id}/invoice`  | `GET`      | `Tes`                 | `getMethodName(String)`                           | None         | None         |
| `/user/signup`              | `POST`     | `UserController`      | `signup(UserDTO, HttpServletResponse)`           | None         | None         |

---


## Tech Stack

**Backend Framework:**
Spring Boot

**Dependencies:**

iText PDF: For PDF generation.

Spring Web: For RESTful API development.

Spring Data JPA: For database operations.

Spring Security: For API authentication and authorization.

**Database:**
MySQL 

