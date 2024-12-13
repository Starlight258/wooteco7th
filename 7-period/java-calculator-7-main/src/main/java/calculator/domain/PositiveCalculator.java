package calculator.domain;

import calculator.exception.CustomIllegalArgumentException;
import calculator.exception.ErrorMessage;
import java.util.List;

public class PositiveCalculator {

    public int add(final List<Integer> numbers) {
        validatePositive(numbers);
        return numbers.stream()
                .mapToInt(number -> number)
                .sum();
    }

    private void validatePositive(final List<Integer> numbers) {
        if (numbers.stream()
                .anyMatch(number -> number <= 0)){
            throw new CustomIllegalArgumentException(ErrorMessage.NON_POSITIVE_NUMBER);
        };
    }
}
