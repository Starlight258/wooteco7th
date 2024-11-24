package christmas.util;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.exception.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("converter 테스트")
class ConverterTest {

    @Test
    void 정수_변환() {
        // Given

        // When & Then
        assertThat(Converter.convertToInteger("3", ErrorMessage.INVALID_DAY)).isEqualTo(3);
    }
}
