package christmas.domain;

import static christmas.support.CustomExceptionAssertions.assertCustomIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Menu.MenuType;
import christmas.exception.ErrorMessage;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("주문 리스트 테스트")
class OrdersTest {

    @Nested
    class 생성 {
        @Test
        void 생성_정상() {
            // Given

            // When
            Orders orders = new Orders(List.of(new Order(Menu.바비큐립, new Quantity(20))));

            // Then
            assertThat(orders.getOrders()).hasSize(1);
        }

        @Test
        void 생성_같은메뉴일경우실패() {
            // Given

            // When & Then
            assertCustomIllegalArgumentException(() -> new Orders(
                    List.of(new Order(Menu.바비큐립, new Quantity(2)), new Order(Menu.바비큐립, new Quantity(1)))))
                    .hasMessageContaining(ErrorMessage.INVALID_ORDER.getMessage());
        }

        @Test
        void 생성_음료만있을경우실패() {
            // Given

            // When & Then
            assertCustomIllegalArgumentException(() -> new Orders(
                    List.of(new Order(Menu.샴페인, new Quantity(2)), new Order(Menu.제로콜라, new Quantity(1)))))
                    .hasMessageContaining(ErrorMessage.INVALID_ORDER.getMessage());
        }

        @Test
        void 생성_메뉴20개초과일경우실패() {
            // Given

            // When & Then
            assertCustomIllegalArgumentException(() -> new Orders(
                    List.of(new Order(Menu.샴페인, new Quantity(20)),
                            new Order(Menu.바비큐립, new Quantity(1)))))
                    .hasMessageContaining(ErrorMessage.INVALID_ORDER.getMessage());
        }
    }

    @Test
    void 같은_타입_메뉴_세기() {
        // Given
        Orders orders = new Orders(
                List.of(new Order(Menu.샴페인, new Quantity(2)), new Order(Menu.제로콜라, new Quantity(2)),
                        new Order(Menu.티본스테이크, new Quantity(2))));

        // When
        int count = orders.countSameTypeMenu(MenuType.DRINK);

        // Then
        assertThat(count).isEqualTo(4);
    }
}
