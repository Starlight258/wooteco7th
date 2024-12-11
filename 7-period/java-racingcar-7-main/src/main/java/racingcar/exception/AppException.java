package racingcar.exception;

public class AppException extends IllegalArgumentException{

    public AppException(final String message) {
        super(ErrorPrefix.format(message));
    }

    public AppException(final ErrorMessage message) {
        super(ErrorPrefix.format(message.getMessage()));
    }
}
