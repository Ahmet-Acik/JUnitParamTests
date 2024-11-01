package org.ahmet;

import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCartUtils {

    public static double calculateAveragePrice(List<Double> prices) {
        if (prices.isEmpty()) return 0.0;
        return prices.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    public static List<Double> filterItemsByPrice(List<Double> prices, double threshold) {
        return prices
                .stream()
                .filter(price -> price > threshold)
                .collect(Collectors
                        .toList());
    }

    public static long countItems(List<Double> prices) {
        return prices.size();
    }

    public static double findMostExpensiveItem(List<Double> prices) {
        return prices
                .stream()
                .mapToDouble(Double::doubleValue)
                .max()
                .orElse(0.0);
    }

    public static double calculateTotalWeight(List<Double> weights) {
        return weights
                .stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public static double calculateTotalPrice(List<Double> prices) {
        return prices
                .stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public static double calculateTotalPriceWithDiscount(List<Double> prices, double discount) {
        return calculateTotalPrice(prices) * (1 - discount);
    }

    public static double calculateTotalPriceWithTax(List<Double> prices, double tax) {
        return calculateTotalPrice(prices) * (1 + tax);
    }

    public static double calculateTotalPriceWithDiscountAndTax(List<Double> prices, double discount, double tax) {
        return calculateTotalPrice(prices) * (1 - discount) * (1 + tax);
    }

    public static double getDiscountRate(DiscountType discountType) {
        switch (discountType) {
            case SEASONAL:
                return 0.1;
            case CLEARANCE:
                return 0.2;
            default:
                return 0.0;
        }
    }
}