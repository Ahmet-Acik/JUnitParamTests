package org.ahmet;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @ParameterizedTest
    @CsvSource({"John, 30, 5000, 60000", "Jane, 25, 7000, 84000"})
    void testCalculateAnnualSalary(String name, int age, double salary, double expectedAnnualSalary) {
        Employee employee = new Employee(name, age, salary);
        assertEquals(expectedAnnualSalary, employee.calculateAnnualSalary(), 1e-9);
    }

    @ParameterizedTest
    @CsvSource({"John, 30, 50000, 5000, 55000", "Jane, 25, 60000, 6000, 66000"})
    void testPromote(String name, int age, double salary, double increaseAmount, double expectedSalary) {
        Employee employee = new Employee(name, age, salary);
        employee.promote(increaseAmount);
        assertEquals(expectedSalary, employee.getSalary(), 1e-9);
    }

    @ParameterizedTest
    @MethodSource("provideEmployeesForAgeCheck")
    void testAgeCheck(Employee employee, int expectedAge) {
        assertEquals(expectedAge, employee.getAge());
    }

    private static Stream<Arguments> provideEmployeesForAgeCheck() {
        return Stream.of(
            Arguments.of(new Employee("John", 30, 50000), 30),
            Arguments.of(new Employee("Jane", 25, 60000), 25)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"John", "Jane"})
    void testNameNotNull(String name) {
        Employee employee = new Employee(name, 30, 50000);
        assertNotNull(employee.getName());
    }

    static class CustomEmployeeProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Arguments.of(new Employee("John", 30, 50000), 30),
                Arguments.of(new Employee("Jane", 25, 60000), 25)
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(CustomEmployeeProvider.class)
    void testWithCustomEmployeeProvider(Employee employee, int expectedAge) {
        assertEquals(expectedAge, employee.getAge());
    }
}