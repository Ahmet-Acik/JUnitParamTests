package org.ahmet;

import java.util.function.Function;
import java.util.function.Predicate;

public class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean validate(Predicate<Product> validator) {
        return validator.test(this);
    }

    public <R> R process(Function<Product, R> processor) {
        return processor.apply(this);
    }
}