# Technical Design

## Implementation Language and Framework

Our project is built using **Java** with the **Spring Framework**. We chose Spring for its robust support of MVC architecture, integrated database tools (Spring Data JPA), and ease of scalability. SQLite is used as the database because of its lightweight nature and easy setup, which makes it ideal for small to mid-scale applications.

---

## Data Storage Plan

We will be using **SQLite** through **Spring Data JPA**. Our app will have the following architecture:

- **Model layer** maps to database entities.
- **Repository layer** handles basic data operations.
- **Service layer** manages business logic.
- **Controller layer** exposes REST endpoints.

All data operations are handled using repositories with default annotations. SQLite is stored as a file (`database.db`) and auto-generated on application startup.

https://www.tutorialspoint.com/sqlite/

https://docs.spring.io/spring-boot/index.html

https://github.com/xerial/sqlite-jdbc

https://restfulapi.net/

---

## Field Description Tables

### User Table

| Field        | Type     | Description               |
|--------------|----------|---------------------------|
| id           | Integer  | Primary key, auto-increment |
| username     | String   | Unique username           |
| email        | String   | User's email address      |
| passwordHash | String   | Hashed password           |
| role         | String   | "admin", "buyer", or "seller" |

### Item Table

| Field        | Type     | Description               |
|--------------|----------|---------------------------|
| id           | Integer  | Primary key, auto-increment |
| name         | String   | Name of the item          |
| description  | String   | Description of the item   |
| price        | Double   | Price in USD              |
| seller_id    | Integer  | Foreign key to User       |
| image_url    | String   | Optional image link       |

### Order Table

| Field        | Type     | Description               |
|--------------|----------|---------------------------|
| id           | Integer  | Primary key               |
| buyer_id     | Integer  | Foreign key to User       |
| item_id      | Integer  | Foreign key to Item       |
| quantity     | Integer  | Number of units ordered   |
| status       | String   | e.g., "pending", "shipped", "delivered" |
| timestamp    | Timestamp| When the order was placed |

---

## Example Data

### Users

| ID | Username   | Email                 | Password (Hashed)        | Role     | Date Joined |
|----|------------|-----------------------|---------------------------|----------|-------------|
| 1  | gamerZack  | zack@gamemail.com     | $2a$10$abc123...          | customer | 2024-12-01  |
| 2  | devAnna    | anna@devmail.com      | $2a$10$def456...          | admin    | 2024-12-05  |
| 3  | playerOne  | player1@gamehub.net   | $2a$10$ghi789...          | customer | 2025-01-02  |
| 4  | ghostNinja | ninja@shadowmail.org  | $2a$10$jkl000...          | customer | 2025-01-15  |

**Notes:**
- `ID`: Primary key
- `Password`: Hash/encrypted
- `Role`: Can be `admin` or `customer`

## Seed Data

INSERT INTO user (id, username, email, password_hash, role)
VALUES (1, 'gamer123', 'gamer@example.com', '...', 'buyer');

INSERT INTO item (id, name, description, price, seller_id)
VALUES (1, 'Dragon Slayer Sword', 'Legendary weapon', 299.99, 2);


## Annotation Rules:


camelCase for variables and methods


PascalCase for classes


Javadoc-style comments for all public methods


Repository and Service classes named with suffixes (UserRepository, ItemService)


Use of [Lombok](https://projectlombok.org/) to reduce repetitive code (e.g., @Getter, @Setter, @AllArgsConstructor)


