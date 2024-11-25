package christmas.domain;

import christmas.exception.CustomIllegalArgumentException;
import christmas.exception.ErrorMessage;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

public class Day {

    private static final int CHRISTMAS = 25;

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
        LocalDate now = toLocalDate();
        return now.getDayOfWeek().equals(DayOfWeek.FRIDAY) || now.getDayOfWeek().equals(DayOfWeek.SATURDAY);
    }

    public boolean isWeekday() {
        return !isWeekend();
    }

    public boolean isSpecialDay() {
        LocalDate now = toLocalDate();
        return now.getDayOfWeek().equals(DayOfWeek.SUNDAY) || value == CHRISTMAS;
    }

    public int diffFromFirstDay() {
        return value - 1;
    }

    public boolean isExceedChristmas() {
        return value > CHRISTMAS;
    }

    public boolean isInDecember() {
        return toLocalDate().getMonth().equals(Month.DECEMBER);
    }

    private void validate(final int value) {
        if (value < 1 || value > 31) {
            throw new CustomIllegalArgumentException(ErrorMessage.INVALID_DAY);
        }
    }

    private LocalDate toLocalDate() {
        return LocalDate.of(year, month, value);
    }

    public int getValue() {
        return value;
    }
}
