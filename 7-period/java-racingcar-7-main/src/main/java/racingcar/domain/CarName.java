package racingcar.domain;

import racingcar.exception.AppException;
import racingcar.exception.ErrorMessage;

public class CarName {

    private static final int LENGTH_MAX_EXCLUSIVE = 5;

    private final String name;

    public CarName(final String name) {
        validate(name);
        this.name = name;
    }

    private void validate(final String name) {
        if (name.length() > LENGTH_MAX_EXCLUSIVE) {
            throw new AppException(ErrorMessage.INVALID_INPUT);
        }
    }
}
