package org.example.model;



import java.util.List;


public class InvoiceResponse {
    public List<InvoiceLine> invoice;
    public double grandTotal;

    public InvoiceResponse(List<InvoiceLine> invoice, double grandTotal) {
        this.invoice = invoice;
        this.grandTotal = grandTotal;
    }

    public List<InvoiceLine> getInvoice() {
        return invoice;
    }

    public void setInvoice(List<InvoiceLine> invoice) {
        this.invoice = invoice;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }
}

