package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.exception.ErrorMessage;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class DayTest {

    private static final int YEAR = 2023;
    private static final int MONTH = 12;

    @Nested
    class 생성_테스트 {
        @ParameterizedTest
        @ValueSource(ints = {1, 31})
        void 생성_성공(int value) {
            // When & Then
            assertThatCode(() -> {
                new Day(2024, 12, value);
            }).doesNotThrowAnyException();
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, 0, 32})
        void 생성_실패(int value) {
            // When & Then
            assertThatThrownBy(() -> new Day(2024, 12, value))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ErrorMessage.INVALID_DAY.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void 주말_테스트(int value) {
        // Given
        Day day = new Day(YEAR, MONTH, value);

        // When & Then
        assertThat(day.isWeekend()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 10, 25})
    void 별날짜_테스트(int value) {
        // Given
        Day day = new Day(YEAR, MONTH, value);

        // When & Then
        assertThat(day.isSpecialDay()).isTrue();
    }

    @ParameterizedTest
    @CsvSource({"2,1", "25,24"})
    void 첫번째날로부터의_차이_테스트(int value, int diff) {
        // Given
        Day day = new Day(YEAR, MONTH, value);

        // When & Then
        assertThat(day.diffFromFirstDay()).isEqualTo(diff);
    }

    @ParameterizedTest
    @CsvSource({"24,false", "25,false", "26,true"})
    void 크리스마스_초과_테스트(int value, boolean expected) {
        // Given
        Day day = new Day(YEAR, MONTH, value);

        // When & Then
        assertThat(day.isExceedChristmas()).isEqualTo(expected);
    }
}
