package org.ahmet;

public class StringUtils {
    public static boolean isPalindrome(String str) {
        return str.contentEquals(new StringBuilder(str).reverse());
    }

    public static boolean isNumberDivisibleByThree(int i) {
        return i % 3 == 0;
    }
}
