package christmas.util;

import christmas.exception.CustomIllegalArgumentException;
import christmas.exception.ErrorMessage;

public class Converter {

    public static int convertToInteger(final String input, final ErrorMessage message) {
        InputValidator.validateNotNullOrBlank(input, message);
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException exception) {
            throw new CustomIllegalArgumentException(message);
        }
    }
}
