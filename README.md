# 🔐 Spring Boot BlogApp API

Comprehensive Blog management API with full CRUD capabilities for posts and categories, supporting JWT-based authentication and role-based access. Integrated with PostgreSQL for persistent data storage and Swagger UI for easy API exploration.

---

## 🚀 Features

- **Blog Post CRUD** (Create, Read, Update, Delete)
- **Category CRUD** (Create, Read, Update, Delete)
- **User authentication with JWT**
- **Role-based access control** (ADMIN, USER)
- **Pagination & Sorting** for listing posts
- **Global exception handling**
- **PostgreSQL integration** for data persistence
- **Preloaded admin user** (via DataInitializer)
- **Swagger UI** for interactive documentation

---

## 🛠 Tech Stack

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Security**
- **JWT (JSON Web Tokens)**
- **Spring Data JPA**
- **PostgreSQL**
- **Springdoc OpenAPI (Swagger UI)**
- **Maven**

---

## 🔗 API Endpoints

| Method    | Endpoint                      | Description                                    | Access                 |
|---------  |-------------------------------|------------------------------------------------|------------------------|
| **POST**  | `/api/auth/register`          | Register a new user                            | PUBLIC                 |
| **POST**  | `/api/auth/login`             | User login with JWT token generation           | PUBLIC                 |
| **POST**  | `/api/posts`                  | Create a new blog post                         | USER, ADMIN            |
| **GET**   | `/api/posts/{id}`             | Get a blog post by ID                          | USER, ADMIN            |
| **GET**   | `/api/posts`                  | Get all blog posts (with pagination & sorting) | USER, ADMIN            |
| **PUT**   | `/api/posts/{id}`             | Update an existing blog post                   | USER (own data), ADMIN |
| **DELETE**| `/api/posts/{id}`             | Delete a blog post                             | USER (own data), ADMIN |
| **POST**  | `/api/categories`             | Create a new category                          | ADMIN, USER       |
| **GET**   | `/api/categories`             | Get all categories                             | ADMIN, USER       |
| **PUT**   | `/api/categories/{id}`        | Update an existing category                    | ADMIN, USER             |
| **DELETE**| `/api/categories/{id}`        | Delete a category                              | ADMIN, USER             |

---

## 🧑‍🔧 Default Users (Pre-loaded for Testing)

| Role   | Email                | Password  |
|--------|----------------------|-----------|
| Admin  | admin@example.com     | admin123  |
| User   | user@example.com      | user123   |

**Note:** Passwords are securely stored using **BCrypt encryption**.

---

## 🔏 Role-Based Access Control

- **ADMIN** can perform all actions (CRUD for posts and categories, manage users).
- **USER** can only create, read, update, and delete their own posts.
- **Only ADMIN** can manage categories and delete users.

---

## ✅ Validation Rules

| Field       | Rule                       |
|-------------|----------------------------|
| `title`     | Required, not blank        |
| `content`   | Required, not blank        |
| `categoryId`| Required                   |
| `name`      | Required, not blank        |
| `email`     | Required, valid format     |
| `password`  | Required, min length 6     |

---

## ❗ Global Exception Handling

| HTTP Code | Error Type         | When It Happens                                |
|-----------|--------------------|-----------------------------------------------|
| **400**   | Bad Request        | Validation fails or invalid payload           |
| **403**   | Forbidden          | Unauthorized access (e.g., non-admin actions) |
| **404**   | Not Found          | Resource (Post/Category/User) doesn't exist   |
| **409**   | Conflict           | Duplicate email                               |

---

## ▶️ Run Locally

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/springboot-blog-api.git

---

## 📘 Swagger UI

Once the application is running, you can view and interact with the API using Swagger UI:

URL: http://localhost:8080/swagger-ui.html

---

## 🧪 Sample JSON (POST/PUT)

**User Registration**

{

  "name": "User",
  
  "username": "user",
  
  "email": "user@example.com",
  
  "password": "user123"

}

**User Login**

{

  "usernameOrEmail": "user@example.com",
  
  "password": "user123"
  
}

**Create Post**

{

  "title": "My First Blog Post",
  
  "description": "This is the description of my first post.",
  
  "content": "This is the content of the post.",
  
  "categoryId": 1
  
}

**Create Category**

{

  "name": "Technology",
  
  "description": "Posts related to technology."
  
}

---

## 👤 Author

AFZAL K A 
📎 GitHub: @afzal-ka

---

📃 License
This project is licensed under the MIT License - see the LICENSE file for details.

