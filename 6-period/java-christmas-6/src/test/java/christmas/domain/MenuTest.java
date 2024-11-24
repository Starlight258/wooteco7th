package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.exception.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuTest {

    @Test
    @DisplayName("메뉴를 이름으로 정상적으로 생성한다.")
    void 생성_성공() {
        // Given

        // When
        Menu menu = Menu.from(Menu.샴페인.name());

        // Then
        assertThat(menu).isEqualTo(Menu.샴페인);
    }

    @Test
    @DisplayName("메뉴 이름이 아니면 예외가 발생한다.")
    void 생성_실패() {
        // Given

        // When & Then
        assertThatThrownBy(() -> Menu.from("유효하지않은메뉴"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.INVALID_ORDER.getMessage());
    }
}
