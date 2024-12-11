package racingcar.exception;

import java.util.function.Supplier;
import racingcar.view.OutputView;

public class ExceptionHandler {

    private final OutputView outputView;

    public ExceptionHandler(final OutputView outputView) {
        this.outputView = outputView;
    }

    public <T> T retryUntilSuccess(Supplier<T> action) {
        while (true) {
            try {
                return action.get();
            } catch (IllegalArgumentException e) {
                outputView.showException(e);
            }
        }
    }
}
