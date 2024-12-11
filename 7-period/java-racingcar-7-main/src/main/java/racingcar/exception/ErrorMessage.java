package racingcar.exception;

public enum ErrorMessage {

    INVALID_(""),
    ;

    private final String message;

    ErrorMessage(final String message) {
        this.message = message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
