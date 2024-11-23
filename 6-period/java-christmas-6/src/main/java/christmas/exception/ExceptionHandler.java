package christmas.exception;

import christmas.view.OutputView;
import java.util.function.Supplier;

public class ExceptionHandler {

    private static final int MAX_RETRY = 3;

    private final OutputView outputView;

    public ExceptionHandler(final OutputView outputView) {
        this.outputView = outputView;
    }

    // 최대 시도 횟수만큼 재시도하며 결과를 반환
    public <T> T retryOn(Supplier<T> action) {
        for (int attempt = 0; attempt < MAX_RETRY; attempt++) {
            try {
                return action.get();
            } catch (IllegalArgumentException e) {
                handleError(e, attempt);
            }
        }
        throw new IllegalArgumentException();
    }

    private void handleError(Exception e, int attempt) {
        if (attempt == MAX_RETRY - 1) {
            throw new IllegalArgumentException();
        }
        outputView.showException((e));
    }
}
