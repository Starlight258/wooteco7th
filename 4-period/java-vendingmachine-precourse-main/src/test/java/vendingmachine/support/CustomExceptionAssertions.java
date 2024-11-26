package vendingmachine.support;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static vendingmachine.exception.ErrorMessage.ERROR_PREFIX;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.ThrowableAssert;

public class CustomExceptionAssertions {

    // IllegalArgumentException
    public static AbstractThrowableAssert<?, ? extends Throwable> assertCustomIllegalArgumentException(
            ThrowableAssert.ThrowingCallable throwingCallable) {
        return assertThatThrownBy(throwingCallable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith(ERROR_PREFIX.getMessage());
    }

    // IllegalStateException
    public static AbstractThrowableAssert<?, ? extends Throwable> assertCustomIllegalStateException(
            ThrowableAssert.ThrowingCallable throwingCallable) {
        return assertThatThrownBy(throwingCallable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageStartingWith(ERROR_PREFIX.getMessage());
    }
}
