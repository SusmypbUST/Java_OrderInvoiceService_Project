# Java_OrderInvoiceService_Project

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

