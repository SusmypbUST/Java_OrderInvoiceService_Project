 
# 🧾 Invoice Generation API

This REST API generates an invoice for a list of purchased items. It applies specific discount and tax rules based on item quantity and category.

---

## 📌 Endpoint

**POST** `/orders/invoice`

### Request Body

```json
[
  {
    "itemName": "Laptop",
    "category": "electronics",
    "quantity": 5,
    "unitPrice": 1000
  },
  {
    "itemName": "T-Shirt",
    "category": "clothing",
    "quantity": 2,
    "unitPrice": 20
  }
]
```
### Response Body
```json
{
  "invoice": [
    {
      "itemName": "Laptop",
      "category": "electronics",
      "quantity": 5,
      "unitPrice": 1000,
      "lineTotal": 5000,
      "discount": 500,
      "tax": 405,
      "total": 4905
    },
    {
      "itemName": "T-Shirt",
      "category": "clothing",
      "quantity": 2,
      "unitPrice": 50,
      "lineTotal": 100,
      "discount": 0,
      "tax": 5,
      "total": 105
    }
  ],
  "grandTotal": 5010
}
```



| Rule No. | Description                                         | Covered By Test Method                     |
| -------- | --------------------------------------------------- | ------------------------------------------ |
| 1        | Line total = quantity × unit price                  | `testLineTotalCalculation`                 |
| 2        | 10% discount if quantity ≥ 5                        | `testDiscountAppliedIfQuantityAtLeastFive` |
| 3        | No discount if quantity < 5                         | `testNoDiscountIfQuantityLessThanFive`     |
| 4        | Tax per category:                                   |                                            |
|          | - Electronics: 9%                                   | `testTaxForElectronics`                    |
|          | - Clothing: 5%                                      | `testTaxForClothing`                       |
|          | - Grocery: 2%                                       | `testTaxForGrocery`                        |
| 5        | Reject items with unitPrice <= 0 or quantity <= 0   | `testInvalidItemsAreFilteredOut`           |
| 6        | Grand total = sum of discounted + taxed line totals | `testGrandTotalCalculation`                |

## Technologies Used

-> Java 17+

-> Spring Boot

-> Maven / Gradle

-> JUnit (for testing)

-> REST (JSON API)

-> Postman


## Project Components

- **Item class**  
  Holds product details: `productName`, `category`, `unitPrice`, and `quantity`.

- **InvoiceRequest class**  
  Contains a list of `Item` objects representing the customer’s order.

- **InvoiceResponse class**  
  Returns the generated invoice with detailed line items and the `grandTotal`.

- **InvoiceService class**  
  Implements business logic:  
  - Calculates line total (`quantity × unitPrice`)  
  - Applies 10% discount if quantity ≥ 5  
  - Applies tax by category (electronics: 9%, clothing: 5%, grocery: 2%)  
  - Filters out invalid items with `unitPrice <= 0` or `quantity <= 0`  
  - Computes grand total from discounted and taxed line totals.

- **InvoiceController class**  
  Handles the `POST /orders/invoice` endpoint and delegates requests to `InvoiceService`.

- **InvoiceTest class**  
  Contains unit tests validating all business rules:  
  - Line total calculation  
  - Discount application based on quantity  
  - Tax calculation per category  
  - Input validation for invalid items  
  - Grand total accuracy


## Running Tests

Unit tests ensure all business rules are correctly implemented and can be run with your preferred test runner.

 







