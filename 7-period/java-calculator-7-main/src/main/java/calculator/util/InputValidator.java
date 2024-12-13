package calculator.util;

import calculator.exception.CustomIllegalArgumentException;
import calculator.exception.ErrorMessage;
import java.util.regex.Pattern;

public class InputValidator {

    private InputValidator() {
    }

    public static void validateNotNull(final String input, final ErrorMessage message) {
        if (input == null) {
            throw new CustomIllegalArgumentException(message);
        }
    }

    public static void validatePattern(String input, Pattern pattern, final ErrorMessage message) {
        if (!pattern.matcher(input).matches()) {
            throw new CustomIllegalArgumentException(message);
        }
    }

}
