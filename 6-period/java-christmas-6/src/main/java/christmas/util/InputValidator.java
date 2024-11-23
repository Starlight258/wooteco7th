package christmas.util;

import christmas.exception.CustomIllegalArgumentException;
import christmas.exception.ErrorMessage;

public class InputValidator {

    public static void validateNotNullOrBlank(final String input) {
        if (input == null) {
            throw new CustomIllegalArgumentException(ErrorMessage.NULL.getMessage());
        }
        if (input.isBlank()) {
            throw new CustomIllegalArgumentException(ErrorMessage.BLANK.getMessage());
        }
    }
}
