package christmas.support;

import christmas.domain.Badge;
import christmas.domain.Day;
import christmas.domain.Order;
import christmas.domain.Orders;
import java.math.BigDecimal;
import java.util.Optional;

public class StringFormatter {

    private static final String TOTAL_PROMOTION_PRICE = "<총혜택 금액>";
    private static final String EXPECT_PRICE = "<할인 후 예상 결제 금액>";
    private static final String BADGE = "<12월 이벤트 배지>";
    private static final String FORMAT = "%s %d개" + System.lineSeparator();
    public static final String NONE = "없음" + System.lineSeparator();
    public static final String ZERO_PRICE = "0원" + System.lineSeparator();

    public String makeOptionalOrderMessage(final Orders orders) {
        StringBuilder message = new StringBuilder();
        for (Order order : orders.getOrders()) {
            message.append(makeOptionalOrderMessage(order));
        }
        return message.toString();
    }

    public String makeOptionalOrderMessage(final Optional<Order> optionalOrder) {
        if (optionalOrder.isEmpty()) {
            return NONE;
        }
        Order order = optionalOrder.get();
        return makeOptionalOrderMessage(order);
    }

    public String makePromotionListMessage(final BigDecimal untilChristmasDiscount, final BigDecimal dayDiscount,
                                           final BigDecimal specialDiscount, final BigDecimal giftDiscount,
                                           final Day visitDay, final Boolean noPromotion) {
        if (noPromotion) {
            return NONE;
        }
        String message = "";
        message += String.format("크리스마스 디데이 할인: -%,.0f원\n", untilChristmasDiscount);
        message += showDayDiscount(visitDay, dayDiscount);
        message += makeSpecialDiscountMessage(specialDiscount);
        message += makeGiftDiscountMessage(giftDiscount);
        return message;
    }

    public String makeTotalPriceMessage(final BigDecimal totalDiscount, final BigDecimal expectPrice,
                                        final Boolean noPromotion) {
        String message = "";
        message += String.format(TOTAL_PROMOTION_PRICE + "\n");
        message += makeTotalDiscountMessage(totalDiscount);
        message += String.format(EXPECT_PRICE + "\n");
        message += makeExpectPriceMessage(expectPrice);
        message += String.format(BADGE + "\n");
        message += makeBadgeMessage(totalDiscount, noPromotion);
        return message;
    }

    private String makeOptionalOrderMessage(final Order order) {
        return String.format(FORMAT, order.getMenu().getName(), order.getQuantity().getValue());
    }

    private static String makeBadgeMessage(final BigDecimal expectPrice, final Boolean noPromotion) {
        if (noPromotion) {
            return NONE;
        }
        return Badge.showName(expectPrice);
    }

    private static String makeSpecialDiscountMessage(final BigDecimal giftDiscount) {
        if (!giftDiscount.equals(BigDecimal.ZERO)) {
            return String.format("특별 할인: -%,.0f원\n", giftDiscount);
        }
        return "";
    }

    private static String makeGiftDiscountMessage(final BigDecimal giftDiscount) {
        if (!giftDiscount.equals(BigDecimal.ZERO)) {
            return String.format("증정 이벤트: -%,.0f원\n", giftDiscount);
        }
        return "";
    }

    private String makeTotalDiscountMessage(final BigDecimal totalDiscount) {
        if (!totalDiscount.equals(BigDecimal.ZERO)) {
            return makeMinusPriceMessage(totalDiscount) + System.lineSeparator();
        }
        return ZERO_PRICE + System.lineSeparator();
    }

    private String makeExpectPriceMessage(final BigDecimal expectPrice) {
        if (!expectPrice.equals(BigDecimal.ZERO)) {
            return makePriceMessage(expectPrice) + System.lineSeparator();
        }
        return NONE;
    }

    private String makeMinusPriceMessage(BigDecimal value) {
        return String.format("-%,.0f원\n", value);
    }

    private String makePriceMessage(BigDecimal value) {
        return String.format("%,.0f원\n", value);
    }


    private String showDayDiscount(final Day visitDay, final BigDecimal dayDiscount) {
        if (dayDiscount.equals(BigDecimal.ZERO)) {
            return "";
        }
        if (visitDay.isWeekend()) {
            return String.format("주말 할인: -%,.0f원\n", dayDiscount);
        }
        return String.format("평일 할인: -%,.0f원\n", dayDiscount);
    }
}
