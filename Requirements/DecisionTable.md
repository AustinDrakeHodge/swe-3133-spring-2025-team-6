| Condition/Rule | Logged in | Items in Cart | Payment Details Entered | Checkout Allowed | Order Complete | Is User Admin | Action                                                               |
|----------------|-----------|---------------|-------------------------|------------------|----------------|---------------|----------------------------------------------------------------------|
| 1              | F         | F             | F                       | F                | F              | F             | Must Register or Log In                                              |
| 2              | T         | F             | F                       | F                | F              | F             | Add to Inventory                                                     |
| 3              | T         | T             | F                       | F                | F              | F             | Enter Payment                                                        |
| 4              | T         | T             | T                       | T                | F              | F             | Show Summary                                                         |
| 5              | T         | T             | T                       | T                | T              | F             | Process Payment, Remove Items from Inventory, Generate Receipt       |
| 6              | T         | N/A           | N/A                     | N/A              | N/A            | T             | Add to Inventory, View/Export Sales Report/ Create Admins from Users |
