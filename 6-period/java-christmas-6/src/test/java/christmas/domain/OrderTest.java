package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Menu.MenuType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("주문 테스트")
class OrderTest {

    @ParameterizedTest
    @CsvSource({"바비큐립, true", "샴페인,false"})
    void 같은_메뉴_확인(String menu, boolean expected) {
        // Given
        Order order = new Order(Menu.바비큐립, new Quantity(2));

        // When
        boolean isSameMenu = order.hasSameMenu(new Order(Menu.from(menu), new Quantity(1)));

        // Then
        assertThat(isSameMenu).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"바비큐립, true", "샴페인,false"})
    void 메뉴_종류_확인(String menu, boolean expected) {
        // Given
        Order order = new Order(Menu.from(menu), new Quantity(2));

        // When
        boolean isSameMenu = order.hasMenuType(MenuType.MAIN);

        // Then
        assertThat(isSameMenu).isEqualTo(expected);
    }
}
