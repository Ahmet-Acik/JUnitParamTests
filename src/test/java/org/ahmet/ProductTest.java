package org.ahmet;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @ParameterizedTest
    @CsvSource({"Laptop, 1000.0, 5, true", "Phone, -500.0, 10, false"})
    void testValidate(String name, double price, int quantity, boolean expected) {
        Product product = new Product(name, price, quantity);
        Predicate<Product> validator = p -> p.getPrice() > 0;
        assertEquals(expected, product.validate(validator));
    }

    @ParameterizedTest
    @CsvSource({"Laptop, 1000.0, 5, 5000.0", "Phone, 500.0, 10, 5000.0"})
    void testProcess(String name, double price, int quantity, double expected) {
        Product product = new Product(name, price, quantity);
        Function<Product, Double> processor = p -> p.getPrice() * p.getQuantity();
        assertEquals(expected, product.process(processor), 1e-9);
    }

    @ParameterizedTest
    @MethodSource("provideProductsForValidation")
    void testWithCustomProvider(Product product, boolean expected) {
        Predicate<Product> validator = p -> p.getPrice() > 0;
        assertEquals(expected, product.validate(validator));
    }

    private static Stream<Arguments> provideProductsForValidation() {
        return Stream.of(
            Arguments.of(new Product("Laptop", 1000.0, 5), true),
            Arguments.of(new Product("Phone", -500.0, 10), false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideProductsForProcessing")
    void testWithCustomProcessor(Product product, double expectedTotal) {
        Function<Product, Double> processor = p -> p.getPrice() * p.getQuantity();
        assertEquals(expectedTotal, product.process(processor), 1e-9);
    }

    private static Stream<Arguments> provideProductsForProcessing() {
        return Stream.of(
            Arguments.of(new Product("Laptop", 1000.0, 5), 5000.0),
            Arguments.of(new Product("Phone", 500.0, 10), 5000.0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideProductsForQuantityCheck")
    void testQuantityCheck(Product product, int expectedQuantity) {
        assertEquals(expectedQuantity, product.getQuantity());
    }

    private static Stream<Arguments> provideProductsForQuantityCheck() {
        return Stream.of(
            Arguments.of(new Product("Laptop", 1000.0, 5), 5),
            Arguments.of(new Product("Phone", 500.0, 10), 10)
        );
    }

    @ParameterizedTest
    @MethodSource("provideProductsForPriceCheck")
    void testPriceCheck(Product product, double expectedPrice) {
        assertEquals(expectedPrice, product.getPrice());
    }

    private static Stream<Arguments> provideProductsForPriceCheck() {
        return Stream.of(
            Arguments.of(new Product("Laptop", 1000.0, 5), 1000.0),
            Arguments.of(new Product("Phone", 500.0, 10), 500.0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideProductsForNameCheck")
    void testNameCheck(Product product, String expectedName) {
        assertEquals(expectedName, product.getName());
    }

    private static Stream<Arguments> provideProductsForNameCheck() {
        return Stream.of(
            Arguments.of(new Product("Laptop", 1000.0, 5), "Laptop"),
            Arguments.of(new Product("Phone", 500.0, 10), "Phone")
        );
    }

    @ParameterizedTest
    @MethodSource("provideProductsForEmptyNameCheck")
    void testEmptyNameCheck(Product product, boolean expected) {
        Predicate<Product> validator = p -> !p.getName().isEmpty();
        assertEquals(expected, product.validate(validator));
    }

    private static Stream<Arguments> provideProductsForEmptyNameCheck() {
        return Stream.of(
            Arguments.of(new Product("Laptop", 1000.0, 5), true),
            Arguments.of(new Product("", 500.0, 10), false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideProductsForEmptyPriceCheck")
    void testEmptyPriceCheck(Product product, boolean expected) {
        Predicate<Product> validator = p -> p.getPrice() > 0;
        assertEquals(expected, product.validate(validator));
    }

    private static Stream<Arguments> provideProductsForEmptyPriceCheck() {
        return Stream.of(
            Arguments.of(new Product("Laptop", 1000.0, 5), true),
            Arguments.of(new Product("Phone", 0.0, 10), false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideProductsForEmptyQuantityCheck")
    void testEmptyQuantityCheck(Product product, boolean expected) {
        Predicate<Product> validator = p -> p.getQuantity() > 0;
        assertEquals(expected, product.validate(validator));
    }

    private static Stream<Arguments> provideProductsForEmptyQuantityCheck() {
        return Stream.of(
            Arguments.of(new Product("Laptop", 1000.0, 5), true),
            Arguments.of(new Product("Phone", 500.0, 0), false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideProductsForEmptyProductCheck")
    void testEmptyProductCheck(Product product, boolean expected) {
        Predicate<Product> validator = p -> !p.getName().isEmpty() && p.getPrice() > 0 && p.getQuantity() > 0;
        assertEquals(expected, product.validate(validator));
    }

    private static Stream<Arguments> provideProductsForEmptyProductCheck() {
        return Stream.of(
            Arguments.of(new Product("Laptop", 1000.0, 5), true),
            Arguments.of(new Product("", 0.0, 0), false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideProductsForProcessingWithDiscount")
    void testWithCustomProcessor_Discount(Product product, double expected) {
        Function<Product, Double> processor = p -> p.getPrice() * p.getQuantity() * 0.9; // 10% discount
        assertEquals(expected, product.process(processor), 1e-9);
    }

    private static Stream<Arguments> provideProductsForProcessingWithDiscount() {
        return Stream.of(
            Arguments.of(new Product("Laptop", 1000.0, 5), 4500.0),
            Arguments.of(new Product("Phone", 500.0, 10), 4500.0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideProductsForProcessingWithIncrease")
    void testWithCustomProcessor_Increase(Product product, double expected) {
        Function<Product, Double> processor = p -> p.getPrice() * p.getQuantity() * 1.1; // 10% increase
        assertEquals(expected, product.process(processor), 1e-9);
    }

    private static Stream<Arguments> provideProductsForProcessingWithIncrease() {
        return Stream.of(
            Arguments.of(new Product("Laptop", 1000.0, 5), 5500.0),
            Arguments.of(new Product("Phone", 500.0, 10), 5500.0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideProductsForProcessingWithDecrease")
    void testWithCustomProcessor_Decrease(Product product, double expected) {
        Function<Product, Double> processor = p -> p.getPrice() * p.getQuantity() * 0.9; // 10% decrease
        assertEquals(expected, product.process(processor), 1e-9);
    }

    private static Stream<Arguments> provideProductsForProcessingWithDecrease() {
        return Stream.of(
            Arguments.of(new Product("Laptop", 1000.0, 5), 4500.0),
            Arguments.of(new Product("Phone", 500.0, 10), 4500.0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideProductsForProcessingWithIncreaseAndDecrease")
    void testWithCustomProcessor_IncreaseAndDecrease(Product product, double expected) {
        Function<Product, Double> processor = p -> p.getPrice() * p.getQuantity() * 1.1 * 0.9; // 10% increase and 10% decrease
        assertEquals(expected, product.process(processor), 1e-9);
    }

    private static Stream<Arguments> provideProductsForProcessingWithIncreaseAndDecrease() {
        return Stream.of(
            Arguments.of(new Product("Laptop", 1000.0, 5), 4950.0),
            Arguments.of(new Product("Phone", 500.0, 10), 4950.0)
        );
    }

}