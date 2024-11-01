package org.ahmet;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @ParameterizedTest
    @CsvSource({"1, 100.0, credit, true", "2, -50.0, debit, false"})
    void testValidate(String id, double amount, String type, boolean expected) {
        Transaction transaction = new Transaction(id, amount, type);
        Predicate<Transaction> validator = t -> t.getAmount() > 0;
        assertEquals(expected, transaction.validate(validator));
    }

    @ParameterizedTest
    @CsvSource({"1, 100.0, credit, 110.0", "2, 200.0, debit, 220.0"})
    void testProcess(String id, double amount, String type, double expected) {
        Transaction transaction = new Transaction(id, amount, type);
        Function<Transaction, Double> processor = t -> t.getAmount() * 1.1;
        assertEquals(expected, transaction.process(processor), 1e-9);
    }

    @ParameterizedTest
    @MethodSource("provideTransactionsForExecution")
    void testExecute(Transaction transaction, double expectedAmount) {
        Consumer<Transaction> executor = t -> t.amount += 10;
        transaction.execute(executor);
        assertEquals(expectedAmount, transaction.getAmount(), 1e-9);
    }

    private static Stream<Arguments> provideTransactionsForExecution() {
        return Stream.of(
            Arguments.of(new Transaction("1", 100.0, "credit"), 110.0),
            Arguments.of(new Transaction("2", 200.0, "debit"), 210.0)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"credit", "debit"})
    void testTypeNotNull(String type) {
        Transaction transaction = new Transaction("1", 100.0, type);
        assertNotNull(transaction.getType());
    }

    @ParameterizedTest
    @MethodSource("provideTransactionsForAmountCheck")
    void testAmountCheck(Transaction transaction, double expectedAmount) {
        assertEquals(expectedAmount, transaction.getAmount());
    }

    private static Stream<Arguments> provideTransactionsForAmountCheck() {
        return Stream.of(
            Arguments.of(new Transaction("1", 100.0, "credit"), 100.0),
            Arguments.of(new Transaction("2", 200.0, "debit"), 200.0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideTransactionsForTypeCheck")
    void testTypeCheck(Transaction transaction, String expectedType) {
        assertEquals(expectedType, transaction.getType());
    }

    private static Stream<Arguments> provideTransactionsForTypeCheck() {
        return Stream.of(
            Arguments.of(new Transaction("1", 100.0, "credit"), "credit"),
            Arguments.of(new Transaction("2", 200.0, "debit"), "debit")
        );
    }

    @ParameterizedTest
    @MethodSource("provideTransactionsForIdCheck")
    void testIdCheck(Transaction transaction, String expectedId) {
        assertEquals(expectedId, transaction.getId());
    }

    private static Stream<Arguments> provideTransactionsForIdCheck() {
        return Stream.of(
            Arguments.of(new Transaction("1", 100.0, "credit"), "1"),
            Arguments.of(new Transaction("2", 200.0, "debit"), "2")
        );
    }

    @ParameterizedTest
    @MethodSource("provideTransactionsForValidation")
    void testWithCustomProvider_AmountGreaterThenZero(Transaction transaction, boolean expected) {  // amount > 0
        Predicate<Transaction> validator = t -> t.getAmount() > 0;
        assertEquals(expected, transaction.validate(validator));
    }

    private static Stream<Arguments> provideTransactionsForValidation() {
        return Stream.of(
            Arguments.of(new Transaction("1", 100.0, "credit"), true),
            Arguments.of(new Transaction("2", -50.0, "debit"), false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideTransactionsForProcessing")
    void testWithCustomProvider_TeenPercentageIncrease(Transaction transaction, double expected) {  // 10% increase
        Function<Transaction, Double> processor = t -> t.getAmount() * 1.1;
        assertEquals(expected, transaction.process(processor), 1e-9);
    }

    private static Stream<Arguments> provideTransactionsForProcessing() {
        return Stream.of(
            Arguments.of(new Transaction("1", 100.0, "credit"), 110.0),
            Arguments.of(new Transaction("2", 200.0, "debit"), 220.0)
        );
    }

}