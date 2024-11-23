package christmas.support;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Day;
import christmas.domain.Menu;
import christmas.domain.Order;
import christmas.domain.Orders;
import christmas.domain.Quantity;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("문자열 포매터 테스트")
class StringFormatterTest {

    private StringFormatter stringFormatter;

    @BeforeEach
    void setUp() {
        stringFormatter = new StringFormatter();
    }

    private Order createOrder(Menu menu, int quantity) {
        return new Order(menu, new Quantity(quantity));
    }

    @Test
    @DisplayName("주문 리스트 문자 생성 테스트")
    void makeOrdersMessage() {
        // Given
        Orders orders = new Orders(List.of(createOrder(Menu.샴페인, 3), createOrder(Menu.바비큐립, 2)));

        // When
        String message = stringFormatter.makeOptionalOrderMessage(orders);

        // Then
        assertThat(message).isEqualTo("샴페인 3개\n바비큐립 2개\n");
    }

    @Nested
    @DisplayName("주문 문자 생성 테스트")
    class optionalOrderTest {
        @Test
        @DisplayName("주문이 존재할 경우 생성한다.")
        void 주문문자생성_주문_존재() {
            // Given
            Optional<Order> orderOptional = Optional.of(createOrder(Menu.샴페인, 3));

            // When
            String message = stringFormatter.makeOptionalOrderMessage(orderOptional);

            // Then
            assertThat(message).isEqualTo("샴페인 3개\n");
        }

        @Test
        @DisplayName("주문이 존재하지 않을 경우 없음을 생성한다")
        void 주문문자생성_주문존재X() {
            // Given
            Optional<Order> orderOptional = Optional.empty();

            // When
            String message = stringFormatter.makeOptionalOrderMessage(orderOptional);

            // Then
            assertThat(message).isEqualTo("없음\n");
        }
    }

    @Nested
    class 할인_내역_테스트 {
        @Test
        void 할인내역_프로모션존재() {
            // Given
            final BigDecimal untilChristmasDiscount = new BigDecimal(1200);
            final BigDecimal dayDiscount = new BigDecimal(4046);
            final BigDecimal specialDiscount = new BigDecimal(1000);
            final BigDecimal giftDiscount = new BigDecimal(25000);
            final Day visitDay = new Day(2023, 12, 3);
            final Boolean noPromotion = false;

            // When
            String message = stringFormatter.makePromotionListMessage(untilChristmasDiscount, dayDiscount,
                    specialDiscount,
                    giftDiscount,
                    visitDay, noPromotion);

            // Then
            assertThat(message).isEqualTo("크리스마스 디데이 할인: -1,200원\n"
                    + "평일 할인: -4,046원\n"
                    + "특별 할인: -1,000원\n"
                    + "증정 이벤트: -25,000원\n");
        }

        @Test
        void 할인내역_프로모션존재X() {
            // Given
            final BigDecimal untilChristmasDiscount = BigDecimal.ZERO;
            final BigDecimal dayDiscount = BigDecimal.ZERO;
            final BigDecimal specialDiscount = BigDecimal.ZERO;
            final BigDecimal giftDiscount = BigDecimal.ZERO;
            final Day visitDay = new Day(2023, 12, 3);
            final Boolean noPromotion = true;

            // When
            String message = stringFormatter.makePromotionListMessage(untilChristmasDiscount, dayDiscount,
                    specialDiscount,
                    giftDiscount,
                    visitDay, noPromotion);

            // Then
            assertThat(message).isEqualTo("없음\n");
        }
    }

    @Test
    void 총금액테스트() {
        // Given
        final BigDecimal totalDiscount = new BigDecimal(31246);
        final BigDecimal expectPrice = new BigDecimal(135754);
        final Boolean noPromotion = false;

        // When
        String message = stringFormatter.makeTotalPriceMessage(totalDiscount, expectPrice, noPromotion);

        // Then
        assertThat(message).isEqualTo("<총혜택 금액>\n"
                + "-31,246원\n"
                + "\n"
                + "<할인 후 예상 결제 금액>\n"
                + "135,754원\n"
                + "\n"
                + "<12월 이벤트 배지>\n"
                + "산타");
    }
}
