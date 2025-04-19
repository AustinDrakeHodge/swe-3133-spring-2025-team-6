## Example Data

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
