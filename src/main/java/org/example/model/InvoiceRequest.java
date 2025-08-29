package org.example.model;


 import java.util.List;


public class InvoiceRequest {
    public List<Item> items;


    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}

