package calculator.exception;

public enum ErrorMessage {

    INVALID_INPUT("입력값이 올바르지 않습니다"),
    ;
    private final String message;

    ErrorMessage(final String message) {
        this.message = message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
