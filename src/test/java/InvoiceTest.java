import org.example.model.Item;
import org.example.model.InvoiceLine;
import org.example.model.InvoiceResponse;
import org.example.service.InvoiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceTest {

    private InvoiceService invoiceService;

    @BeforeEach
    void setUp() {
        invoiceService = new InvoiceService();
    }

    @Test
    void testLineTotalCalculation() {
        Item item = createItem("Phone", "Electronics", 500.0, 3);
        InvoiceResponse response = invoiceService.generateInvoice(List.of(item));
        InvoiceLine line = response.invoice.get(0);

        assertEquals(1500.0, line.lineTotal);
    }

    @Test
    void testDiscountAppliedIfQuantityAtLeastFive() {
        Item item = createItem("Jeans", "Clothing", 100.0, 5); // qualifies for discount
        InvoiceResponse response = invoiceService.generateInvoice(List.of(item));
        InvoiceLine line = response.invoice.get(0);

        assertEquals(500.0, line.lineTotal);
        assertEquals(50.0, line.discount, 0.01); // 10% of 500
    }

    @Test
    void testNoDiscountIfQuantityLessThanFive() {
        Item item = createItem("Shirt", "Clothing", 100.0, 2);
        InvoiceResponse response = invoiceService.generateInvoice(List.of(item));
        InvoiceLine line = response.invoice.get(0);

        assertEquals(200.0, line.lineTotal);
        assertEquals(0.0, line.discount);
    }

    @Test
    void testTaxForElectronics() {
        Item item = createItem("Laptop", "Electronics", 1000.0, 1);
        InvoiceResponse response = invoiceService.generateInvoice(List.of(item));
        InvoiceLine line = response.invoice.get(0);

        assertEquals(180.0, line.tax, 0.01); // 18% of 1000
    }

    @Test
    void testTaxForClothing() {
        Item item = createItem("Jacket", "Clothing", 200.0, 1);
        InvoiceResponse response = invoiceService.generateInvoice(List.of(item));
        InvoiceLine line = response.invoice.get(0);

        assertEquals(24.0, line.tax, 0.01); // 12% of 200
    }

    @Test
    void testTaxForGrocery() {
        Item item = createItem("Rice", "Grocery", 100.0, 2); // 200 total
        InvoiceResponse response = invoiceService.generateInvoice(List.of(item));
        InvoiceLine line = response.invoice.get(0);

        assertEquals(10.0, line.tax, 0.01); // 5% of 200
    }

    @Test
    void testGrandTotalCalculation() {
        List<Item> items = Arrays.asList(
                createItem("Phone", "Electronics", 500.0, 1),  // 500 + 18% tax = 590
                createItem("Jeans", "Clothing", 100.0, 5),     // 500 - 50 = 450 + 54 = 504
                createItem("Rice", "Grocery", 50.0, 10)        // 500 - 50 = 450 + 22.5 = 472.5
        );

        InvoiceResponse response = invoiceService.generateInvoice(items);

        double expectedTotal = response.invoice.stream()
                .mapToDouble(line -> line.finalAmount)
                .sum();

        assertEquals(expectedTotal, response.grandTotal, 0.01);
    }

    @Test
    void testInvalidItemsAreFilteredOut() {
        List<Item> items = Arrays.asList(
                createItem("Bad Item 1", "Electronics", 0.0, 2), // Invalid: unitPrice = 0
                createItem("Bad Item 2", "Grocery", 10.0, 0),    // Invalid: quantity = 0
                createItem("Good Item", "Clothing", 100.0, 1)    // Valid
        );

        InvoiceResponse response = invoiceService.generateInvoice(items);

        assertEquals(1, response.invoice.size());
        assertEquals("Good Item", response.invoice.get(0).productName);
    }

    // Helper method
    private Item createItem(String name, String category, double unitPrice, int quantity) {
        Item item = new Item();
        item.productName = name;
        item.category = category;
        item.unitPrice = unitPrice;
        item.quantity = quantity;
        return item;
    }
}
