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
}