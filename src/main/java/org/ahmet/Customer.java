package org.ahmet;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Customer {
    private String name;
    private List<Product> orders;

    public Customer(String name, List<Product> orders) {
        this.name = name;
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public List<Product> getOrders() {
        return orders;
    }

    public boolean validate(Predicate<Customer> validator) {
        return validator.test(this);
    }

    public <R> R process(Function<Customer, R> processor) {
        return processor.apply(this);
    }
}