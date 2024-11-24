package christmas.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import christmas.domain.Menu;
import christmas.domain.Order;
import christmas.domain.Orders;
import christmas.domain.Quantity;
import christmas.exception.ErrorMessage;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ServiceTest {

    @Test
    @DisplayName("주문이 정상적으로 생성된다.")
    void 주문_생성() {
        // Given
        List<String> input = Arrays.asList("타파스-2", "제로콜라-3");
        Service service = new Service();

        // When
        Orders orders = service.createOrders(input);

        // Then
        assertAll(
                () -> assertThat(orders.getOrders()).hasSize(2),
                () -> assertThat(orders.getOrders()).containsExactly(new Order(Menu.타파스, new Quantity(2)),
                        new Order(Menu.제로콜라, new Quantity(3)))
        );
    }

    @Test
    @DisplayName("주문 형식이 다를 경우 예외가 발생한다.")
    void 주문_생성_실패() {
        // Given
        List<String> input = Arrays.asList("타파스-2-2", "제로콜라-3");
        Service service = new Service();

        // When & Then
        assertThatThrownBy(() -> service.createOrders(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.INVALID_ORDER.getMessage());
    }
}
