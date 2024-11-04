package org.ahmet;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @ParameterizedTest
    @CsvSource({"12345, 1000, 500, 1500", "67890, 2000, 1000, 3000"})
    void testDeposit(String accountNumber, double initialBalance, double depositAmount, double expectedBalance) {
        Account account = new Account(accountNumber, initialBalance);
        account.deposit(depositAmount);
        assertEquals(expectedBalance, account.getBalance(), 1e-9);
    }

    @ParameterizedTest
    @MethodSource("provideAccountsForWithdrawal")
    void testWithdraw(Account account, double amount, double expectedBalance) {
        account.withdraw(amount);
        assertEquals(expectedBalance, account.getBalance(), 1e-9);
    }

    private static Stream<Arguments> provideAccountsForWithdrawal() {
        return Stream.of(
                Arguments.of(new Account("12345", 1000), 500, 500),
                Arguments.of(new Account("67890", 2000), 1000, 1000)
        );
    }

    @ParameterizedTest
    @CsvSource({"12345, 1000, 67890, 500, 500, 1500", "67890, 2000, 12345, 1000, 1000, 3000"})
    void testTransferFunds(String sourceAccountNumber, double sourceInitialBalance, String targetAccountNumber, double transferAmount, double expectedSourceBalance, double expectedTargetBalance) {
        Account sourceAccount = new Account(sourceAccountNumber, sourceInitialBalance);
        Account targetAccount = new Account(targetAccountNumber, expectedTargetBalance - transferAmount);
        sourceAccount.transferFunds(targetAccount, transferAmount);
        assertEquals(expectedSourceBalance, sourceAccount.getBalance(), 1e-9);
        assertEquals(expectedTargetBalance, targetAccount.getBalance(), 1e-9);
    }

    @ParameterizedTest
    @CsvSource({"12345, 1000, 5, 1050", "67890, 2000, 10, 2200"})
    void testApplyInterest(String accountNumber, double initialBalance, double interestRate, double expectedBalance) {
        Account account = new Account(accountNumber, initialBalance);
        account.applyInterest(interestRate);
        assertEquals(expectedBalance, account.getBalance(), 1e-9);
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345", "67890"})
    void testAccountNumberNotNull(String accountNumber) {
        Account account = new Account(accountNumber, 1000);
        assertNotNull(account.getAccountNumber());
    }

    static class CustomAccountProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(new Account("12345", 1000), 1000),
                    Arguments.of(new Account("67890", 2000), 2000)
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(CustomAccountProvider.class)
    void testWithCustomAccountProvider(Account account, double expectedBalance) {
        assertEquals(expectedBalance, account.getBalance(), 1e-9);
    }

    @ParameterizedTest
    @MethodSource("provideAccountsForBalanceCheck")
    void testBalanceCheck(Account account, double expectedBalance) {
        assertEquals(expectedBalance, account.getBalance(), 1e-9);
    }

    private static Stream<Arguments> provideAccountsForBalanceCheck() {
        return Stream.of(
                Arguments.of(new Account("12345", 1000), 1000),
                Arguments.of(new Account("67890", 2000), 2000)
        );
    }

    @ParameterizedTest
    @MethodSource("provideAccountsForAccountNumberCheck")
    void testAccountNumberCheck(Account account, String expectedAccountNumber) {
        assertEquals(expectedAccountNumber, account.getAccountNumber());
    }

    private static Stream<Arguments> provideAccountsForAccountNumberCheck() {
        return Stream.of(
                Arguments.of(new Account("12345", 1000), "12345"),
                Arguments.of(new Account("67890", 2000), "67890")
        );
    }

}