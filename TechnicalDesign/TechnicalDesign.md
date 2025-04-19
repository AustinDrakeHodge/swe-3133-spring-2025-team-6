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

## [Field Description Tables](/TechnicalDesign/TableFieldDescriptions.md)

---

## Example Data

---

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


