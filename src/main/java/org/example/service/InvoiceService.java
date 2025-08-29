package org.example.service;

import org.example.model.Item;
import org.example.model.InvoiceLine;
import org.example.model.InvoiceResponse;
 import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Service
public class InvoiceService {



    public InvoiceResponse generateInvoice(List<Item> items) {



        List<InvoiceLine> invoiceLines = items.stream()
                .filter(item -> item.quantity > 0 && item.unitPrice > 0)
                .map(item -> {
                    double lineTotal = item.unitPrice * item.quantity;
                    double discount = item.quantity >= 5 ? lineTotal * 0.10 : 0.0;
                    double discountedTotal = lineTotal - discount;
                    double taxRate;
                    switch (item.category.toLowerCase()) {
                        case "electronics":
                            taxRate = 0.18;
                            break;
                        case "clothing":
                            taxRate = 0.12;
                            break;
                        case "grocery":
                            taxRate = 0.05;
                            break;
                        default:
                            taxRate = 0.0;
                    }
                    double tax = discountedTotal * taxRate;
                    double finalAmount = discountedTotal + tax;

                    InvoiceLine line = new InvoiceLine();
                    line.productName = item.productName;
                    line.category = item.category;
                    line.unitPrice = item.unitPrice;
                    line.quantity = item.quantity;
                    line.lineTotal = lineTotal;
                    line.discount = discount;
                    line.tax = tax;
                    line.finalAmount = finalAmount;

                    return line;
                })
                .collect(Collectors.toList());

        double grandTotal = invoiceLines.stream()
                .mapToDouble(line -> line.finalAmount)
                .sum();

        return new InvoiceResponse(invoiceLines, grandTotal);
    }
}
