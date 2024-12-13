package calculator.exception;

public enum ErrorMessage {

    INVALID_INPUT("입력값이 올바르지 않습니다"),
    NOT_POSITIVE_NUMBER("양수값이 아닙니다"),
    INVALID_INPUT_INTEGER("정수 문자열이 아닙니다."),
    INVALID_DELIMITER_DUPLICATED("구분자가 서로 중복됩니다"),
    ;
    private final String message;

    ErrorMessage(final String message) {
        this.message = message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
