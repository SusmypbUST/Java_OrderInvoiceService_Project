package org.example.controller;



import org.example.model.InvoiceRequest;
import org.example.model.InvoiceResponse;
import org.example.model.Item;
import org.example.service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

//    @PostMapping("/invoice")
//    public ResponseEntity<InvoiceResponse> generateInvoice(@RequestBody InvoiceRequest request) {
//        try {
//            InvoiceResponse response = invoiceService.generateInvoice(request.items);
//            return ResponseEntity.ok(response);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }

    @PostMapping("/invoice")
    public ResponseEntity<InvoiceResponse> generateInvoice() {

        // Hardcoded items list
        Item item1 = new Item();
        item1.productName = "Laptop";
        item1.category = "Electronics";
        item1.unitPrice = 1200.00;
        item1.quantity = 1;

        Item item2 = new Item();
        item2.productName = "Mouse";
        item2.category = "Accessories";
        item2.unitPrice = 25.50;
        item2.quantity = 2;

        List<Item> items = List.of(item1, item2);

        try {
            InvoiceResponse response = invoiceService.generateInvoice(items);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}

