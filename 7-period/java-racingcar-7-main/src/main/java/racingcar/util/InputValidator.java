package racingcar.util;

import java.util.regex.Pattern;
import racingcar.exception.AppException;
import racingcar.exception.ErrorMessage;

public class InputValidator {

    private InputValidator() {
    }

    public static void validateNotNullOrBlank(final String input, final ErrorMessage message) {
        if (input == null || input.isBlank()) {
            throw new AppException(message);
        }
    }

    public static boolean isInvalidPattern(String input, Pattern pattern) {
        return !pattern.matcher(input).matches();
    }
}
