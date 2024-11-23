package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class BadgeTest {

    @Test
    void 배지얻기_못얻음() {
        // Given
        BigDecimal price = new BigDecimal(3000);

        // When & Then
        assertThat(Badge.showName(price)).isEqualTo("없음");
    }

    @Test
    void 배지얻기_별() {
        // Given
        BigDecimal price = new BigDecimal(5000);

        // When & Then
        assertThat(Badge.showName(price)).isEqualTo("별");
    }

    @Test
    void 배지얻기_토끼() {
        // Given
        BigDecimal price = new BigDecimal(10000);

        // When & Then
        assertThat(Badge.showName(price)).isEqualTo("토끼");
    }

    @Test
    void 배지얻기_산타() {
        // Given
        BigDecimal price = new BigDecimal(20000);

        // When & Then
        assertThat(Badge.showName(price)).isEqualTo("산타");
    }
}
