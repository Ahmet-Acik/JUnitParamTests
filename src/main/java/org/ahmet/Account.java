package org.ahmet;

public class Account {
    private String accountNumber;
    private double balance;

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            this.balance -= amount;
        } else {
            throw new IllegalArgumentException("Insufficient balance");
        }
    }

    public void transferFunds(Account targetAccount, double amount) {
        if (amount <= balance) {
            this.withdraw(amount);
            targetAccount.deposit(amount);
        } else {
            throw new IllegalArgumentException("Insufficient balance");
        }
    }

    public void applyInterest(double interestRate) {
        this.balance += this.balance * interestRate / 100;
    }
}