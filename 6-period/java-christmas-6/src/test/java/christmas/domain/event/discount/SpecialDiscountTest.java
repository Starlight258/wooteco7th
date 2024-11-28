package christmas.domain.event.discount;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Day;
import christmas.domain.Menu;
import christmas.domain.Quantity;
import christmas.domain.order.Order;
import christmas.domain.order.Orders;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SpecialDiscountTest {

    @DisplayName("할인 금액을 계산한다.")
    @ParameterizedTest
    @CsvSource({"3,1000", "4,0", "25,1000"})
    void calculateAmount(int day, int expected) {
        // Given
        SpecialDiscount discount = createDiscount(day, Menu.바비큐립, 2);

        // When
        BigDecimal amount = discount.calculateAmount();

        // Then
        assertThat(amount).isEqualTo(new BigDecimal(expected));
    }

    @DisplayName("최소금액을 만족하지 못하면 할인받을 수 없다.")
    @Test
    void calculateAmountUnderAmount() {
        // Given
        SpecialDiscount discount = createDiscount(1, Menu.타파스, 1);

        // When
        BigDecimal amount = discount.calculateAmount();

        // Then
        assertThat(amount).isEqualTo(new BigDecimal(0));
    }

    @Test
    @DisplayName("이름을 반환한다.")
    void getName() {
        // Given
        SpecialDiscount discount = createDiscount(1, Menu.타파스, 1);

        // When & Then
        assertThat(discount.getName()).isEqualTo("특별 할인");
    }

    private SpecialDiscount createDiscount(final int day, final Menu menu, final int value) {
        return new SpecialDiscount(new Day(2023, 12, day),
                createOrders(menu, value));
    }

    private Orders createOrders(final Menu menu, final int value) {
        return new Orders(List.of(new Order(menu, new Quantity(value))));
    }
}
