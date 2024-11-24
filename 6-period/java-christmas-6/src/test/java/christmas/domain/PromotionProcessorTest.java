package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("프로모션 프로세서 테스트")
class PromotionProcessorTest {

    private PromotionProcessor promotionProcessor;

    private Day makeDay(int day) {
        return new Day(2023, 12, day);
    }

    private Orders makeOrders(Menu menu, int quantity) {
        return new Orders(List.of(new Order(menu, new Quantity(quantity))));
    }

    private void makeProcessor(Day day, Orders orders) {
        promotionProcessor = new PromotionProcessor(day, orders);
    }

    @Test
    @DisplayName("이벤트 적용 최소 금액인지 확인한다.")
    void checkAtLeastPrice() {
        // Given
        makeProcessor(makeDay(3), makeOrders(Menu.바비큐립, 2));

        // When & Then
        assertThat(promotionProcessor.checkAtLeastPrice()).isTrue();
    }

    @Nested
    @DisplayName("크리스마스 디데이 할인 테스트")
    class christmasDdayDiscountTest {

        @Test
        @DisplayName("크리스마스가 지났을 경우 0원")
        void checkUntilChristmasDiscount() {
            // Given
            makeProcessor(makeDay(26), makeOrders(Menu.바비큐립, 2));

            // When
            BigDecimal discount = promotionProcessor.checkUntilChristmasDiscount();

            // Then
            assertThat(discount).isEqualTo(BigDecimal.ZERO);
        }

        @Test
        @DisplayName("크리스마스 지나기 전이면 할인 금액 구현")
        void 크리스마스_지나기전_할인금액구현() {
            // Given
            makeProcessor(makeDay(25), makeOrders(Menu.바비큐립, 2));

            // When
            BigDecimal discount = promotionProcessor.checkUntilChristmasDiscount();

            // Then
            assertThat(discount).isEqualTo(new BigDecimal(3400));
        }
    }
    
    @Nested
    class dayDiscountTest{
        @Test
        @DisplayName("평일 : 디저트 할인")
        void 평일할인_디저트() {
            // Given
            makeProcessor(makeDay(4), makeOrders(Menu.아이스크림, 3));

            // When
            BigDecimal discount = promotionProcessor.checkDayDiscount();

            // Then
            assertThat(discount).isEqualTo(new BigDecimal(2023 * 3));
        }

        @Test
        @DisplayName("주말 : 메인 메뉴 할인")
        void 주말할인_메인() {
            // Given
            makeProcessor(makeDay(2), makeOrders(Menu.티본스테이크, 3));

            // When
            BigDecimal discount = promotionProcessor.checkDayDiscount();

            // Then
            assertThat(discount).isEqualTo(new BigDecimal(2023 * 3));
        }
    }
   

    @Test
    void checkSpecialDiscount() {
    }

    @Test
    void checkGift() {
    }

    @Test
    void calculateExpectPrice() {
    }

    @Test
    void makeGiftDiscount() {
    }
}
