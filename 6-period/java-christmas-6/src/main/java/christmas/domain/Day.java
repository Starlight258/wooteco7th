package christmas.domain;

import christmas.exception.CustomIllegalArgumentException;
import christmas.exception.ErrorMessage;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class Day {

    private final int year;
    private final int month;

    private final int value;

    public Day(final int year, final int month, final int value) {
        validate(value);
        this.year = year;
        this.month = month;
        this.value = value;
    }

    public boolean isWeekend() {
        LocalDate now = LocalDate.of(year, month, value);
        return now.getDayOfWeek().equals(DayOfWeek.FRIDAY) || now.getDayOfWeek().equals(DayOfWeek.SATURDAY);
    }

    public boolean isSpecialDay() {
        LocalDate now = LocalDate.of(year, month, value);
        return now.getDayOfWeek().equals(DayOfWeek.SUNDAY) || value == 25;
    }

    private void validate(final int value) {
        if (value < 1 || value > 31) {
            throw new CustomIllegalArgumentException(ErrorMessage.INVALID_DAY.getMessage());
        }
    }

    public int diffFromFirstDay() {
        return value - 1;
    }

    public int getValue() {
        return value;
    }

    public boolean isExceedChristmas() {
        return value > 25;
    }
}
