package org.ahmet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParameterizedTests {

    @Test
    void isPalindromeTest() {
        assertTrue(StringUtils.isPalindrome("madam"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"madam", "racecar", "applelppa", "level"})
    void isPalindromeTest(String str) {
        assertTrue(StringUtils.isPalindrome(str));
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 6, 9, 12})
    void isNumberDivisibleByThreeTest() {
        assertTrue(StringUtils.isNumberDivisibleByThree(3));
    }


    @ParameterizedTest
    @ValueSource(ints = {3, 6, 9, 12})
    void isNumberDivisibleByThreeTest(int i) {
        assertTrue(i % 3 == 0);
    }


    @ParameterizedTest
    @NullSource
    //@NullSource: This annotation provides a null value as an argument to the parameterized test. The test method will be executed with null as the input
    @EmptySource
        //@EmptySource: This annotation provides an empty value as an argument to the parameterized test. The test method will be executed with an empty value as the input
    void isNullOrEmpty(String str) {
        assertTrue(str == null || str.isEmpty());  //The combination of @NullSource and @EmptySource means that the isNullOrEmpty method will be executed twice: once with null and once with an empty string as the input.
    }


    @ParameterizedTest
    @MethodSource("numberToMonthProvider")
    void getMonthNameTest(int month, String monthName) {
        assertEquals(monthName, DateUtils.getMonthName(month));
    }

    /*

    This method, numberToMonth, is a static method that provides a stream of arguments for a parameterized test in JUnit 5. Here's a breakdown of each part:
    Method Signature:
    private static Stream<Arguments> numberToMonth(): This defines a private static method that returns a Stream of Arguments. The Arguments class is used to pass multiple parameters to a parameterized test.
    Return Statement:
    return Stream.of(...): This creates a stream of Arguments using the Stream.of method. Each Arguments.of call creates a new set of arguments.
    Arguments:
    Arguments.of(1, "January"), Arguments.of(2, "February"), etc.: Each Arguments.of call creates a pair of arguments. The first argument is an integer representing the month number, and the second argument is a string representing the month name.
    The method returns a stream of pairs, where each pair consists of a month number and its corresponding month name. This stream can be used in a parameterized test to test the getMonthName method with different inputs.

     */
    private static Stream<Arguments> numberToMonth() {
        return Stream.of(
                Arguments.of(1, "January"),
                Arguments.of(2, "February"),
                Arguments.of(3, "March"),
                Arguments.of(4, "April"),
                Arguments.of(5, "May"),
                Arguments.of(6, "June"),
                Arguments.of(7, "July"),
                Arguments.of(8, "August"),
                Arguments.of(9, "September"),
                Arguments.of(10, "October"),
                Arguments.of(11, "November"),
                Arguments.of(12, "December")
        );
    }

    /*
    This method, numberToMonthProvider, is a static method that provides an array of objects for a parameterized test in JUnit 5. Here's a breakdown of each part:
    Method Signature:
    static Object[] numberToMonthProvider(): This defines a static method that returns an array of Object. The method is intended to be used as a source of arguments for a parameterized test.
    Return Statement:
    return new Object[] {...}: This creates and returns an array of Object. Each element in this array is itself an array of Object.
    Inner Arrays:
    new Object[] {1, "January"}, new Object[] {2, "February"}, etc.: Each inner array represents a pair of arguments. The first element is an integer representing the month number, and the second element is a string representing the month name.
    The method returns an array of pairs, where each pair consists of a month number and its corresponding month name. This array can be used in a parameterized test to test a method with different inputs.

     */
    static Object[] numberToMonthProvider() {
        return new Object[]{
                new Object[]{1, "January"},
                new Object[]{2, "February"},
                new Object[]{3, "March"},
                new Object[]{4, "April"},
                new Object[]{5, "May"},
                new Object[]{6, "June"},
                new Object[]{7, "July"},
                new Object[]{8, "August"},
                new Object[]{9, "September"},
                new Object[]{10, "October"},
                new Object[]{11, "November"},
                new Object[]{12, "December"}
        };
    }


}