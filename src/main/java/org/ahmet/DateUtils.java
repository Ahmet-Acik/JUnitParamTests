package org.ahmet;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class DateUtils {
    public static boolean isLeapYear(int year) {
        if (year % 4 != 0) {
            return false;
        } else if (year % 100 != 0) {
            return true;
        } else {
            return year % 400 == 0;
        }
    }

    public static String getMonthName(int month) {
        return LocalDate.of(2021, month, 1)
                .getMonth()
                .getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    public static int getDaysInMonth(int month) {
        switch (month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31;
            case 4: case 6: case 9: case 11:
                return 30;
            case 2:
                return 28;
            default:
                throw new IllegalArgumentException("Invalid month number");
        }
    }

}