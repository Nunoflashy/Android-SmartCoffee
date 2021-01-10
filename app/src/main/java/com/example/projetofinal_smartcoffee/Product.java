package com.example.projetofinal_smartcoffee;

public class Product {
    public Product() {}

    public Product(String name, String category, int available, float price) {
        this.name = name;
        this.category = category;
        this.status = available;
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

    public String getInfo() {
        return String.format("id:%d - %s (%.2f€) (Category: %s) (Available:%d)",
                id, name, price, category, status);
    }

    private int id;
    private String name;
    private String category;
    private int status;
    private float price;
}
