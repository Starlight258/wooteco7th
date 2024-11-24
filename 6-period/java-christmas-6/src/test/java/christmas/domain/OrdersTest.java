package christmas.domain;

import static christmas.support.CustomExceptionAssertions.assertCustomIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Menu.MenuType;
import christmas.exception.ErrorMessage;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("주문 리스트 테스트")
class OrdersTest {

    @Nested
    class 원소_추가 {
        @Test
        void 원소_추가_정상() {
            // Given
            Orders orders = new Orders(List.of(new Order(Menu.바비큐립, new Quantity(2))));

            // When
            orders.add(new Order(Menu.샴페인, new Quantity(1)));

            // Then
            assertThat(orders.getOrders()).hasSize(2);
        }

        @Test
        void 원소_추가_같은메뉴일경우실패() {
            // Given
            Orders orders = new Orders(List.of(new Order(Menu.바비큐립, new Quantity(2))));

            // When & Then
            assertCustomIllegalArgumentException(() -> orders.add(new Order(Menu.바비큐립, new Quantity(1))))
                    .hasMessageContaining(ErrorMessage.INVALID_ORDER.getMessage());
        }
    }


    @ParameterizedTest
    @MethodSource
    void 음료_메뉴만있는지_테스트(Orders orders, boolean hasOnlyDrink) {
        // Given

        // When & Then
        assertThat(orders.checkOnlyDrinks()).isEqualTo(hasOnlyDrink);
    }

    private static Stream<Arguments> 음료_메뉴만있는지_테스트() {
        return Stream.of(
                Arguments.of(new Orders(List.of(new Order(Menu.샴페인, new Quantity(2)))), true),
                Arguments.of(new Orders(List.of(new Order(Menu.타파스, new Quantity(2)))), false));
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
