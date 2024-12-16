package models ;

import java.sql.Date;

public class Product {
    private int id;
    private String name;
    private String description;
    private String imageLink;
    private int quantity;
    private int price;
    private String date;

    // Constructor
    public Product(int id, String name, String description, String imageLink, int quantity, int price, String date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageLink = imageLink;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
