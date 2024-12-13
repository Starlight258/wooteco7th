package calculator.domain;

import java.util.List;

public class Calculator {

    public int add(final List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(number -> number)
                .sum();
    }
}
