package racingcar.domain;

import racingcar.exception.AppException;
import racingcar.exception.ErrorMessage;

public class Attempt {

    private int value;

    public Attempt(final int value) {
        validate(value);
        this.value = value;
    }

    private void validate(final int value) {
        if (value <= 0) {
            throw new AppException(ErrorMessage.INVALID_INPUT);
        }
    }

    public boolean isZero() {
        return value == 0;
    }

    public void decreaseAttempt() {
        value--;
    }
}
