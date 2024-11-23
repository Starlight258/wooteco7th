package christmas.domain;

import christmas.exception.CustomIllegalArgumentException;
import christmas.exception.ErrorMessage;

public class Quantity {

    private final int value;

    public Quantity(final int value) {
        validate(value);
        this.value = value;
    }

    private void validate(final int value) {
        if (value < 1 || value > 20) {
            throw new CustomIllegalArgumentException(ErrorMessage.INVALID_ORDER.getMessage());
        }
    }

    public int getValue() {
        return value;
    }
}
