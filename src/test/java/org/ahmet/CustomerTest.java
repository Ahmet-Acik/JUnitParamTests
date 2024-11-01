package org.ahmet;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @ParameterizedTest
    @MethodSource("provideCustomersForValidation")
    void testValidate(Customer customer, boolean expected) {
        Predicate<Customer> validator = c -> c.getOrders()
                .stream()
                .allMatch(p -> p.getPrice() > 0);
        assertEquals(expected, customer.validate(validator));
    }

    @ParameterizedTest
    @MethodSource("provideCustomersForProcessing")
    void testProcess(Customer customer, double expectedTotal) {
        Function<Customer, Double> processor = c -> c.getOrders()
                .stream()
                .mapToDouble(Product::getPrice).sum();
        assertEquals(expectedTotal, customer.process(processor), 1e-9);
    }

    private static Stream<Arguments> provideCustomersForValidation() {
        return Stream.of(
                Arguments.of(new Customer("Alice", Arrays.asList(new Product("Laptop", 1000.0, 1), new Product("Phone", 500.0, 1))), true),
                Arguments.of(new Customer("Bob", Arrays.asList(new Product("Laptop", 1000.0, 1), new Product("Phone", -500.0, 1))), false)
        );
    }

    private static Stream<Arguments> provideCustomersForProcessing() {
        return Stream.of(
                Arguments.of(new Customer("Alice", Arrays.asList(new Product("Laptop", 1000.0, 1), new Product("Phone", 500.0, 1))), 1500.0),
                Arguments.of(new Customer("Bob", Arrays.asList(new Product("Laptop", 1000.0, 1), new Product("Phone", 500.0, 1))), 1500.0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideCustomersForEmptyOrders")
    void testValidateEmptyOrders(Customer customer, boolean expected) {
        Predicate<Customer> validator = c -> c.getOrders()
                .stream()
                .allMatch(p -> p.getPrice() > 0);
        assertEquals(expected, customer.validate(validator));
    }

    @ParameterizedTest
    @MethodSource("provideCustomersForDiscountedOrders")
    void testProcessWithDiscount(Customer customer, double expectedTotal) {
        Function<Customer, Double> processor = c -> c.getOrders()
                .stream()
                .mapToDouble(p -> p.getPrice() * 0.9).sum(); // 10% discount
        assertEquals(expectedTotal, customer.process(processor), 1e-9);
    }

    private static Stream<Arguments> provideCustomersForEmptyOrders() {
        return Stream.of(
                Arguments.of(new Customer("Charlie", Arrays.asList()), true)
        );
    }

    private static Stream<Arguments> provideCustomersForDiscountedOrders() {
        return Stream.of(
                Arguments.of(new Customer("Alice", Arrays.asList(new Product("Laptop", 1000.0, 1), new Product("Phone", 500.0, 1))), 1350.0),
                Arguments.of(new Customer("Bob", Arrays.asList(new Product("Laptop", 1000.0, 1), new Product("Phone", 500.0, 1))), 1350.0)
        );
    }

}