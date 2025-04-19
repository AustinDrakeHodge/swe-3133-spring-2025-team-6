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

## User

| Field    | Type           | Nullable | Key        | Default | Relationship                        | Description                     |
|----------|----------------|----------|------------|---------|-------------------------------------|---------------------------------|
| UserID   | int            | No       | Primary Key|         | Has ShoppingCart, InventoryItem, Order | Unique identifier for a user    |
| Username | nvarchar(32)   | No       |            |         |                                     | User's login name               |
| Password | nvarchar(64)   | No       |            |         |                                     | User's hashed password          |
| Email    | nvarchar(255)  | No       |            |         |                                     | Contact email                   |
| isAdmin  | boolean        | No       |            | false   | Admin of InventoryItem             | Determines if the user is admin |

## InventoryItem

| Field       | Type           | Nullable | Key               | Default | Relationship                        | Description                        |
|-------------|----------------|----------|-------------------|---------|-------------------------------------|------------------------------------|
| ItemID      | int            | No       | Primary Key       |         | Linked to ItemPicture, CartItem, OrderItem | Unique identifier for each item       |
| UserID      | int            | No       | Foreign Key (User)|         | References User                     | The owner (admin) of the item       |
| ItemName    | nvarchar(128)  | No       |                   |         |                                     | Name/title of the item              |
| Price       | money(32)      | No       |                   |         |                                     | Selling price of the item           |
| Description | nvarchar(500)  | Yes      |                   | NULL    |                                     | A short description of the item     |
| Type        | nvarchar(20)   | No       |                   |         |                                     | Category/type of the item           |
| isSold      | boolean        | No       |                   | false   |                                     | Indicates if the item is sold       |

## ItemPicture

| Field     | Type           | Nullable | Key                    | Default | Relationship              | Description               |
|-----------|----------------|----------|------------------------|---------|---------------------------|---------------------------|
| PictureID | int            | No       | Primary Key            |         |                           | Unique ID for picture     |
| ItemID    | int            | No       | Foreign Key (InventoryItem) |   | References InventoryItem | Related item image        |
| FilePath  | nvarchar(256)  | No       |                        |         |                           | Path to image file        |

## ShoppingCart

| Field       | Type       | Nullable | Key                         | Default | Relationship                     | Description                           |
|-------------|------------|----------|-----------------------------|---------|----------------------------------|---------------------------------------|
| CartID      | int        | No       | Primary Key                 |         | Has CartItems, Creates Order     | Unique ID for shopping cart           |
| UserID      | int        | No       | Foreign Key (User)          |         | References User                  | Owner of the shopping cart            |
| CartItemID  | int        | No       | Foreign Key (CartItem)      |         | References CartItem              | Current item(s) in the cart           |
| isCheckedOut| boolean    | No       |                             | false   |                                  | Indicates if cart was submitted       |

## CartItem

| Field      | Type     | Nullable | Key                          | Default | Relationship              | Description                            |
|------------|----------|----------|------------------------------|---------|---------------------------|----------------------------------------|
| CartItemID | int      | No       | Primary Key                  |         | Matches InventoryItem     | Unique identifier for cart item        |
| CartID     | int      | No       | Foreign Key (ShoppingCart)   |         | References ShoppingCart   | The cart this item belongs to          |
| ItemID     | int      | No       | Foreign Key (InventoryItem)  |         | References InventoryItem  | The item in the cart                   |

## ShippingInformation

| Field         | Type           | Nullable | Key         | Default | Relationship        | Description                       |
|---------------|----------------|----------|-------------|---------|---------------------|-----------------------------------|
| ShippingID    | int            | No       | Primary Key |         | Directs Order        | Unique ID for shipping info       |
| AddressLine   | nvarchar(90)   | No       |             |         |                     | Street address                    |
| City          | nvarchar(85)   | No       |             |         |                     | City of destination               |
| State         | nvarchar(85)   | No       |             |         |                     | State of destination              |
| ZipCode       | nvarchar(12)   | No       |             |         |                     | Postal code                       |
| Country       | nvarchar(85)   | No       |             |         |                     | Destination country               |
| ShippingSpeed | nvarchar(20)   | No       |             |         |                     | Standard, express, etc.           |
| ShippingCost  | decimal(3)     | No       |             | 0.0     |                     | Cost of shipping                  |

## PaymentInformation

| Field            | Type           | Nullable | Key         | Default | Relationship     | Description                         |
|------------------|----------------|----------|-------------|---------|------------------|-------------------------------------|
| PaymentID        | int            | No       | Primary Key |         | Pays Order       | Unique ID for payment method        |
| CreditCardNumber | nvarchar(20)   | No       |             |         |                  | Card number                         |
| ExpirationMonth  | int            | No       |             |         |                  | Expiry month                        |
| ExpirationYear   | int            | No       |             |         |                  | Expiry year                         |
| CVV              | nvarchar(3)    | No       |             |         |                  | Security code                       |
| PhoneNumber      | nvarchar(14)   | No       |             |         |                  | Contact number                      |

## Order

| Field      | Type         | Nullable | Key                                | Default            | Relationship                          | Description                           |
|------------|--------------|----------|------------------------------------|--------------------|---------------------------------------|---------------------------------------|
| OrderID    | int          | No       | Primary Key                        |                    | Contains OrderItems                   | Unique order identifier                |
| UserID     | int          | No       | Foreign Key (User)                 |                    | References User                       | The customer placing the order         |
| CartID     | int          | No       | Foreign Key (ShoppingCart)        |                    | References ShoppingCart               | The cart associated with the order     |
| ShippingID | int          | No       | Foreign Key (ShippingInformation) |                    | References ShippingInformation        | Shipping address                       |
| PaymentID  | int          | No       | Foreign Key (PaymentInformation) |                    | References PaymentInformation         | Payment method                         |
| Subtotal   | money(32)    | No       |                                    | 0.0                |                                       | Pre-tax total                          |
| Tax        | money(32)   | No       |                                    | 0.0                |                                       | Tax amount                             |
| GrandTotal | money(32)    | No       |                                    | 0.0                |                                       | Final total after tax                  |
| Last4CC    | varchar(4)   | No       |                                    |                    |                                       | Last 4 digits of card used             |
| OrderTime  | date         | No       |                                    | CURRENT_TIMESTAMP  |                                       | Timestamp of order                     |

## OrderItem

| Field       | Type  | Nullable | Key                       | Default | Relationship           | Description                        |
|-------------|-------|----------|---------------------------|---------|------------------------|------------------------------------|
| OrderItemID | int   | No       | Primary Key               |         | Records InventoryItem  | Unique ID for order-item pair      |
| OrderID     | int   | No       | Foreign Key (Order)       |         | References Order       | Which order the item belongs to    |
| ItemID      | int   | No       | Foreign Key (InventoryItem)|        | References InventoryItem| Which item is in this order        |

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


