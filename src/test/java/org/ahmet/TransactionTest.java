package org.ahmet;

import org.junit.jupiter.api.extension.ExtensionContext;
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
}