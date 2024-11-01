package org.ahmet;

public class Employee {
    private String name;
    private int age;
    private double salary;

    public Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }

    public void giveRaise(double percentage) {
        this.salary += this.salary * percentage / 100;
    }

    public double calculateAnnualSalary() {
        return this.salary * 12;
    }

    public void promote(double increaseAmount) {
        this.salary += increaseAmount;
    }
}