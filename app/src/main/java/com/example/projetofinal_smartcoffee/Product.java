package com.example.projetofinal_smartcoffee;

import com.example.projetofinal_smartcoffee.Database.ProductStatus;

public class Product {
    public Product() {}

    public Product(String name, String category, int available, float price) {
        this.name = name;
        this.category = category;
        this.status = status;
        this.price = price;
    }

    public Product(int id, String name, String category, int available, float price) {
        this(name, category, available, price);
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getAvailability() {
        return status;
    }

    public float getPrice() {
        return price;
    }

    private int id;
    private String name;
    private String category;
    int status;
    float price;
}
