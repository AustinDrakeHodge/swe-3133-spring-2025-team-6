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

## [Example Data](/TechnicalDesign/ExampleData.md)

---

## Seed Data

### How to Use .sql Files with Spring Data JPA and SQLite
### Setup Checklist:
Spring Boot project with dependencies:

spring-boot-starter-data-jpa

SQLite JDBC driver (e.g., org.xerial:sqlite-jdbc)

application.properties or application.yml configured to use SQLite

SQL files:

[ItemShopCreateTables.sql](/TechnicalDesign/ItemShopCreateTables.sql) – for creating tables

[ItemShopInsertData.sql](/TechnicalDesign/ItemShopInsertData.sql) – for inserting sample data

### Step-by-Step Integration
1. Add SQLite JDBC Dependency

xml
Copy
Edit
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.36.0.3</version>
</dependency>

2. Create Your SQL Files
In your src/main/resources folder, add:

[ItemShopCreateTables.sql](/TechnicalDesign/ItemShopCreateTables.sql) → All your CREATE TABLE statements

[ItemShopInsertData.sql](/TechnicalDesign/ItemShopInsertData.sql) → All your INSERT INTO statements

3. Configure application.properties
properties
Copy
Edit
spring.datasource.url=jdbc:sqlite:mydatabase.db
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.datasource.initialization-mode=always
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true

---

## Annotation Rules:


camelCase for variables and methods


PascalCase for classes


Javadoc-style comments for all public methods


Repository and Service classes named with suffixes (UserRepository, ItemService)


Use of [Lombok](https://projectlombok.org/) to reduce repetitive code (e.g., @Getter, @Setter, @AllArgsConstructor)


