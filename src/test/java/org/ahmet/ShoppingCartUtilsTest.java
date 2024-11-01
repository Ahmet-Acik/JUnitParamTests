package org.ahmet;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Test class for ShoppingCartUtils.
 */
class ShoppingCartUtilsTest {

    /**
     * Tests the calculateAveragePrice method with various price lists.
     *
     * @param prices   the list of prices
     * @param expected the expected average price
     */
    @ParameterizedTest
    @MethodSource("providePricesForAverage")
    void calculateAveragePrice(List<Double> prices, double expected) {
        assertEquals(expected, ShoppingCartUtils.calculateAveragePrice(prices), 1e-9);
    }

    /**
     * Provides test data for calculateAveragePrice.
     *
     * @return a stream of arguments containing price lists and their expected average prices
     */
    private static Stream<Arguments> providePricesForAverage() {
        return Stream.of(of(Arrays.asList(10.0, 20.0, 30.0), 20.0), of(Collections.emptyList(), 0.0));
    }

    /**
     * Tests the filterItemsByPrice method with various price lists and thresholds.
     *
     * @param prices    the list of prices
     * @param threshold the price threshold
     * @param expected  the expected filtered list of prices
     */
    @ParameterizedTest
    @CsvSource({"'10.0,20.0,30.0', 15.0, '20.0,30.0'", "'10.0,20.0,30.0', 35.0, ''"})
    void filterItemsByPrice(String prices, double threshold, String expected) {
        List<Double> priceList = (prices == null || prices.isEmpty()) ? Collections.emptyList() : Arrays.stream(prices.split(",")).map(Double::valueOf).collect(Collectors.toList());
        List<Double> expectedList = (expected == null || expected.isEmpty()) ? Collections.emptyList() : Arrays.stream(expected.split(",")).map(Double::valueOf).collect(Collectors.toList());
        assertEquals(expectedList, ShoppingCartUtils.filterItemsByPrice(priceList, threshold));
    }

    /**
     * Tests the countItems method with various price lists.
     *
     * @param prices   the list of prices
     * @param expected the expected count of items
     */
    @ParameterizedTest
    @CsvSource({"'10.0,20.0,30.0', 3", "'', 0"})
    void countItems(String prices, long expected) {
        List<Double> priceList = (prices == null || prices.isEmpty()) ? Collections.emptyList() : Arrays.stream(prices.split(",")).map(Double::valueOf).collect(Collectors.toList());
        assertEquals(expected, ShoppingCartUtils.countItems(priceList));
    }

    /**
     * Tests the findMostExpensiveItem method with various price lists.
     *
     * @param prices   the list of prices
     * @param expected the expected most expensive item
     */
    @ParameterizedTest
    @CsvSource({"'10.0,20.0,30.0', 30.0", "'', 0.0"})
    void findMostExpensiveItem(String prices, double expected) {
        List<Double> priceList = (prices == null || prices.isEmpty()) ? Collections.emptyList() : Arrays.stream(prices.split(",")).map(Double::valueOf).collect(Collectors.toList());
        assertEquals(expected, ShoppingCartUtils.findMostExpensiveItem(priceList), 1e-9);
    }

    /**
     * Tests the calculateTotalWeight method with various weight lists.
     *
     * @param weights  the list of weights
     * @param expected the expected total weight
     */
    @ParameterizedTest
    @CsvSource({"'1.0,2.0,3.0', 6.0", "'', 0.0"})
    void calculateTotalWeight(String weights, double expected) {
        List<Double> weightList = (weights == null || weights.isEmpty()) ? Collections.emptyList() : Arrays.stream(weights.split(",")).map(Double::valueOf).collect(Collectors.toList());
        assertEquals(expected, ShoppingCartUtils.calculateTotalWeight(weightList), 1e-9);
    }

    /**
     * Tests the calculateTotalPrice method with various price lists.
     *
     * @param prices   the list of prices
     * @param expected the expected total price
     */
    @ParameterizedTest
    @CsvSource({"'10.0,20.0,30.0', 60.0", "'', 0.0"})
    void calculateTotalPrice(String prices, double expected) {
        List<Double> priceList = (prices == null || prices.isEmpty()) ? Collections.emptyList() : Arrays.stream(prices.split(",")).map(Double::valueOf).collect(Collectors.toList());
        assertEquals(expected, ShoppingCartUtils.calculateTotalPrice(priceList), 1e-9);
    }

    /**
     * Tests the calculateTotalPriceWithDiscount method with various price lists and discounts.
     *
     * @param prices   the list of prices
     * @param discount the discount rate
     * @param expected the expected total price after discount
     */
    @ParameterizedTest
    @CsvSource({"'10.0,20.0,30.0', 0.1, 54.0", "'', 0.1, 0.0"})
    void calculateTotalPriceWithDiscount(String prices, double discount, double expected) {
        List<Double> priceList = (prices == null || prices.isEmpty()) ? Collections.emptyList() : Arrays.stream(prices.split(",")).map(Double::valueOf).collect(Collectors.toList());
        assertEquals(expected, ShoppingCartUtils.calculateTotalPriceWithDiscount(priceList, discount), 1e-9);
    }

    /**
     * Tests the calculateTotalPriceWithTax method with various price lists and tax rates.
     *
     * @param prices   the list of prices
     * @param tax      the tax rate
     * @param expected the expected total price after tax
     */
    @ParameterizedTest
    @CsvSource({"'10.0,20.0,30.0', 0.1, 66.0", "'', 0.1, 0.0"})
    void calculateTotalPriceWithTax(String prices, double tax, double expected) {
        List<Double> priceList = (prices == null || prices.isEmpty()) ? Collections.emptyList() : Arrays.stream(prices.split(",")).map(Double::valueOf).collect(Collectors.toList());
        assertEquals(expected, ShoppingCartUtils.calculateTotalPriceWithTax(priceList, tax), 1e-9);
    }

    /**
     * Tests the calculateTotalPriceWithDiscountAndTax method with various price lists, discounts, and tax rates.
     *
     * @param prices   the list of prices
     * @param discount the discount rate
     * @param tax      the tax rate
     * @param expected the expected total price after discount and tax
     */
    @ParameterizedTest
    @CsvSource({"'10.0,20.0,30.0', 0.1, 0.1, 59.4", "'', 0.1, 0.1, 0.0"})
    void calculateTotalPriceWithDiscountAndTax(String prices, double discount, double tax, double expected) {
        List<Double> priceList = (prices == null || prices.isEmpty()) ? Collections.emptyList() : Arrays.stream(prices.split(",")).map(Double::valueOf).collect(Collectors.toList());
        assertEquals(expected, ShoppingCartUtils.calculateTotalPriceWithDiscountAndTax(priceList, discount, tax), 1e-9);
    }

    /**
     * Tests the filterItemsByPrice method with various price lists and thresholds.
     *
     * @param prices    the list of prices
     * @param threshold the price threshold
     * @param expected  the expected filtered list of prices
     */
    @ParameterizedTest
    @MethodSource("providePricesForFilter")
    void filterItemsByPrice(List<Double> prices, double threshold, List<Double> expected) {
        assertEquals(expected, ShoppingCartUtils.filterItemsByPrice(prices, threshold));
    }

    /**
     * Provides test data for filterItemsByPrice.
     *
     * @return a stream of arguments containing price lists, thresholds, and expected filtered lists
     */
    private static Stream<Arguments> providePricesForFilter() {
        return Stream.of(of(Arrays.asList(10.0, 20.0, 30.0), 15.0, Arrays.asList(20.0, 30.0)), of(Arrays.asList(10.0, 20.0, 30.0), 35.0, Collections.emptyList()));
    }

    /**
     * Tests the countItems method with various price lists.
     *
     * @param prices   the list of prices
     * @param expected the expected count of items
     */
    @ParameterizedTest
    @MethodSource("providePricesForCount")
    void countItems(List<Double> prices, long expected) {
        assertEquals(expected, ShoppingCartUtils.countItems(prices));
    }

    /**
     * Provides test data for countItems.
     *
     * @return a stream of arguments containing price lists and their expected counts
     */
    private static Stream<Arguments> providePricesForCount() {
        return Stream.of(of(Arrays.asList(10.0, 20.0, 30.0), 3L), of(Collections.emptyList(), 0L));
    }

    /**
     * Tests the findMostExpensiveItem method with various price lists.
     *
     * @param prices   the list of prices
     * @param expected the expected most expensive item
     */
    @ParameterizedTest
    @MethodSource("providePricesForMostExpensive")
    void findMostExpensiveItem(List<Double> prices, double expected) {
        assertEquals(expected, ShoppingCartUtils.findMostExpensiveItem(prices), 1e-9);
    }

    /**
     * Provides test data for findMostExpensiveItem.
     *
     * @return a stream of arguments containing price lists and their expected most expensive items
     */
    private static Stream<Arguments> providePricesForMostExpensive() {
        return Stream.of(of(Arrays.asList(10.0, 20.0, 30.0), 30.0), of(Collections.emptyList(), 0.0));
    }

    /**
     * Tests the calculateTotalWeight method with various weight lists.
     *
     * @param weights  the list of weights
     * @param expected the expected total weight
     */
    @ParameterizedTest
    @MethodSource("provideWeightsForTotalWeight")
    void calculateTotalWeight(List<Double> weights, double expected) {
        assertEquals(expected, ShoppingCartUtils.calculateTotalWeight(weights), 1e-9);
    }

    /**
     * Provides test data for calculateTotalWeight.
     *
     * @return a stream of arguments containing weight lists and their expected total weights
     */
    private static Stream<Arguments> provideWeightsForTotalWeight() {
        return Stream.of(of(Arrays.asList(1.0, 2.0, 3.0), 6.0), of(Collections.emptyList(), 0.0));
    }

    /**
     * Tests the calculateTotalPrice method with various price lists.
     *
     * @param prices   the list of prices
     * @param expected the expected total price
     */
    @ParameterizedTest
    @MethodSource("providePricesForTotalPrice")
    void calculateTotalPrice(List<Double> prices, double expected) {
        assertEquals(expected, ShoppingCartUtils.calculateTotalPrice(prices), 1e-9);
    }

    /**
     * Provides test data for calculateTotalPrice.
     *
     * @return a stream of arguments containing price lists and their expected total prices
     */
    private static Stream<Arguments> providePricesForTotalPrice() {
        return Stream.of(of(Arrays.asList(10.0, 20.0, 30.0), 60.0), of(Collections.emptyList(), 0.0));
    }

    /**
     * Tests the calculateTotalPriceWithDiscount method with various price lists and discounts.
     *
     * @param prices   the list of prices
     * @param discount the discount rate
     * @param expected the expected total price after discount
     */
    @ParameterizedTest
    @MethodSource("providePricesForTotalPriceWithDiscount")
    void calculateTotalPriceWithDiscount(List<Double> prices, double discount, double expected) {
        assertEquals(expected, ShoppingCartUtils.calculateTotalPriceWithDiscount(prices, discount), 1e-9);
    }

    /**
     * Provides test data for calculateTotalPriceWithDiscount.
     *
     * @return a stream of arguments containing price lists, discount rates, and their expected total prices after discount
     */
    private static Stream<Arguments> providePricesForTotalPriceWithDiscount() {
        return Stream.of(of(Arrays.asList(10.0, 20.0, 30.0), 0.1, 54.0), of(Collections.emptyList(), 0.1, 0.0));
    }

    /**
     * Tests the calculateTotalPriceWithTax method with various price lists and tax rates.
     *
     * @param prices   the list of prices
     * @param tax      the tax rate
     * @param expected the expected total price after tax
     */
    @ParameterizedTest
    @MethodSource("providePricesForTotalPriceWithTax")
    void calculateTotalPriceWithTax(List<Double> prices, double tax, double expected) {
        assertEquals(expected, ShoppingCartUtils.calculateTotalPriceWithTax(prices, tax), 1e-9);
    }

    /**
     * Provides test data for calculateTotalPriceWithTax.
     *
     * @return a stream of arguments containing price lists, tax rates, and their expected total prices after tax
     */
    private static Stream<Arguments> providePricesForTotalPriceWithTax() {
        return Stream.of(of(Arrays.asList(10.0, 20.0, 30.0), 0.1, 66.0), of(Collections.emptyList(), 0.1, 0.0));
    }

    /**
     * Tests the calculateTotalPriceWithDiscountAndTax method with various price lists, discounts, and tax rates.
     *
     * @param prices   the list of prices
     * @param discount the discount rate
     * @param tax      the tax rate
     * @param expected the expected total price after discount and tax
     */
    @ParameterizedTest
    @MethodSource("providePricesForTotalPriceWithDiscountAndTax")
    void calculateTotalPriceWithDiscountAndTax(List<Double> prices, double discount, double tax, double expected) {
        assertEquals(expected, ShoppingCartUtils.calculateTotalPriceWithDiscountAndTax(prices, discount, tax), 1e-9);
    }

    /**
     * Provides test data for calculateTotalPriceWithDiscountAndTax.
     *
     * @return a stream of arguments containing price lists, discount rates, tax rates, and their expected total prices after discount and tax
     */
    private static Stream<Arguments> providePricesForTotalPriceWithDiscountAndTax() {
        return Stream.of(of(Arrays.asList(10.0, 20.0, 30.0), 0.1, 0.1, 59.4), of(Collections.emptyList(), 0.1, 0.1, 0.0));
    }

    /**
     * Tests the calculateTotalPrice method with data from a CSV file.
     *
     * @param prices   the list of prices as a comma-separated string
     * @param expected the expected total price
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/test-data.csv", numLinesToSkip = 1)
    void testWithCsvFileSource(String prices, double expected) {
        List<Double> priceList = (prices == null || prices.isEmpty()) ? Collections.emptyList() : Arrays.stream(prices.split(",")).map(Double::valueOf).collect(Collectors.toList());
        assertEquals(expected, ShoppingCartUtils.calculateTotalPrice(priceList), 1e-9);
    }

    /**
     * Tests the getDiscountRate method with various discount types.
     *
     * @param discountType the discount type
     */
    @ParameterizedTest
    @EnumSource(DiscountType.class)
    void testWithEnumSource(DiscountType discountType) {
        double discount = ShoppingCartUtils.getDiscountRate(discountType);
        assertTrue(discount >= 0 && discount <= 1);
    }

    /**
     * Tests the price list parsing with various string inputs.
     *
     * @param prices the list of prices as a comma-separated string
     */
    @ParameterizedTest
    @ValueSource(strings = {"10.0,20.0,30.0", "5.0,15.0,25.0"})
    void testWithValueSource(String prices) {
        List<Double> priceList = Arrays.stream(prices.split(",")).map(Double::valueOf).collect(Collectors.toList());
        assertNotNull(priceList);
        assertFalse(priceList.isEmpty());
    }

    /**
     * Custom arguments provider for parameterized tests.
     */
    static class CustomArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of("10.0,20.0,30.0", 60.0),
                Arguments.of("5.0,15.0,25.0", 45.0)
            );
        }
    }

    /**
     * Tests the calculateTotalPrice method with custom arguments.
     *
     * @param prices   the list of prices as a comma-separated string
     * @param expected the expected total price
     */
    @ParameterizedTest
    @ArgumentsSource(CustomArgumentsProvider.class)
    void testWithArgumentsSource(String prices, double expected) {
        List<Double> priceList = Arrays.stream(prices.split(",")).map(Double::valueOf).collect(Collectors.toList());
        assertEquals(expected, ShoppingCartUtils.calculateTotalPrice(priceList), 1e-9);
    }
}