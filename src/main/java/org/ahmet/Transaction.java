package org.ahmet;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Transaction {
    private String id;
    double amount;
    private String type; // e.g., "credit" or "debit"

    public Transaction(String id, double amount, String type) {
        this.id = id;
        this.amount = amount;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public boolean validate(Predicate<Transaction> validator) {
        return validator.test(this);
    }

    public <R> R process(Function<Transaction, R> processor) {
        return processor.apply(this);
    }

    public void execute(Consumer<Transaction> executor) {
        executor.accept(this);
    }
}