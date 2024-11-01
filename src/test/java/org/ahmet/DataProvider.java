package org.ahmet;

import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;

public class DataProvider {


    private static Stream<Arguments> numberToMonthProvider() {
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
}
