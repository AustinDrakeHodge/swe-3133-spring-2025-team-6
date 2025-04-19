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

# Example Data

## User Table
|   UserID | Username   | Password   | Email                 | isAdmin   |
|----------|------------|------------|-----------------------|-----------|
|        1 | LinkMaster | pass123    | link@example.com      | True      |
|        2 | MageQueen  | magic456   | zelda@example.com     | False     |
|        3 | PotionGuy  | heals789   | alchemist@example.com | False     |
|        4 | SwordHero  | slash000   | hero@example.com      | True      |
|        5 | ShieldBoi  | block999   | defender@example.com  | False     |

## InventoryItem Table
|   ItemID |   UserID | ItemName       |   Price | Description                              | Type       | isSold   |
|----------|----------|----------------|---------|------------------------------------------|------------|----------|
|        1 |        1 | Master Sword   |  299.99 | Legendary sword with magical properties. | Sword      | False    |
|        2 |        2 | Book of Flames |  150.00    | A magic book that casts fire spells.     | Magic Book | False    |
|        3 |        3 | Healing Potion |   19.99 | Restores 50 HP.                          | Potion     | True     |
|        4 |        4 | Dragon Shield  |  199.99 | Provides high defense against fire.      | Shield     | False    |
|        5 |        5 | Mana Potion    |   17.50  | Restores 30 MP.                          | Potion     | False    |

## ItemPicture Table
|   PictureID |   ItemID | FilePath                  |
|-------------|----------|---------------------------|
|           1 |        1 | images/master_sword.png   |
|           2 |        2 | images/book_of_flames.png |
|           3 |        3 | images/healing_potion.png |
|           4 |        4 | images/dragon_shield.png  |
|           5 |        5 | images/mana_potion.png    |

## Cart Table
|   CartID |   UserID |   isCheckedOut |
|----------|----------|--------------|
|        1 |        2 |  False        | 
|        2 |        3 |  True         | 
|        3 |        4 |  False        | 
|        4 |        5 |  False        | 
|        5 |        1 |  True         | 

## CartItem Table
|   CartItemID |   CartID |   ItemID |
|--------------|----------|----------|
|            1 |        1 |        1 |
|            2 |        2 |        3 |
|            3 |        3 |        2 |
|            4 |        4 |        4 |
|            5 |        5 |        5 |

## ShippingInformation Table
|   ShippingID | AddressLine      | City           | State   |   ZipCode | Country        | ShippingSpeed   |   ShippingCost |
|--------------|------------------|----------------|---------|-----------|----------------|-----------------|----------------|
|            1 | 123 Triforce Ln  | Hyrule         | Central       |     12345 | Hyrule Kingdom              | Overnight      |           29.00 |
|            2 | 456 Magic Rd     | Los Angeles    | California    |     23456 | United States of America    | Ground        |           0.00 |
|            3 | 789 Healing Blvd | Holly Springs  | Georgia          |     34567 | United States of America | 3-day         |           19.00 |
|            4 | 321 Shield St    | New York City | New York          |     45678 | United States of America | Overnight     |           29.00 |
|            5 | 654 Mana Ave     | Jacksonville  | Florida          |     56789 | United States of America  | 3-day         |           19.00 |

## PaymentInformation Table
|   PaymentID |   CreditCardNumber |   ExpirationMonth |   ExpirationYear |   CVV | PhoneNumber   |
|-------------|--------------------|-------------------|------------------|-------|---------------|
|           1 |  1234-5678-9012-3456 |                 5 |             2025 |   123 | 555-123-4567  |
|           2 |  2345-6789-0123-4567 |                 6 |             2026 |   234 | 555-234-5678  |
|           3 |  3456-7890-1234-5678 |                 7 |             2027 |   345 | 555-345-6789  |
|           4 |  4567-8901-2345-6789 |                 8 |             2028 |   456 | 555-456-7890  |
|           5 |  5678-9012-3456-7890 |                 9 |             2029 |   567 | 555-567-8901  |

## Order Table
|   OrderID |   UserID |   CartID |   ShippingID |   PaymentID |   Subtotal |   Tax |   GrandTotal |   Last4CC | OrderTime   |
|-----------|----------|----------|--------------|-------------|------------|-------|--------------|-----------|-------------|
|         1 |        2 |        1 |            1 |           1 |     299.99 |  24.00   |       323.99 |      3456 | 2025-04-01  |
|         2 |        3 |        2 |            2 |           2 |      19.99 |   1.60 |        21.59 |      4567 | 2025-04-02  |
|         3 |        4 |        3 |            3 |           3 |     150    |  12.00   |       162    |      5678 | 2025-04-03  |
|         4 |        5 |        4 |            4 |           4 |      17.5  |   1.40 |        18.9  |      6789 | 2025-04-04  |
|         5 |        1 |        5 |            5 |           5 |     199.99 |  16.00   |       215.99 |      7890 | 2025-04-05  |

## OrderItem Table
|   OrderItemID |   OrderID |   ItemID |
|---------------|-----------|----------|
|             1 |         1 |        1 |
|             2 |         2 |        3 |
|             3 |         3 |        2 |
|             4 |         4 |        5 |
|             5 |         5 |        4 |


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


