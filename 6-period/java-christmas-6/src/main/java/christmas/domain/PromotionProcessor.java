package christmas.domain;

import christmas.domain.Menu.MenuType;
import java.math.BigDecimal;
import java.util.Optional;

public class PromotionProcessor {

    private static final int MIN_EVENT_APPLY_PRICE = 10_000;
    private static final int DISCOUNT_START_PRICE = 1_000;
    private static final int DISCOUNT_DAY_UNIT = 100;
    private static final int DISCOUNT_PER_MENU = 2023;
    private static final int MIN_GIFT_PRICE = 120_000;

    private final Day visitDay;
    private final Orders orders;

    public PromotionProcessor(final Day visitDay, final Orders orders) {
        this.visitDay = visitDay;
        this.orders = orders;
    }

    public boolean checkAtLeastPrice() {
        return orders.calculateTotalPrice().compareTo(new BigDecimal(MIN_EVENT_APPLY_PRICE)) >= 0;
    }

    public BigDecimal checkUntilChristmasDiscount() {
        if (visitDay.isExceedChristmas()) {
            return BigDecimal.ZERO;
        }
        BigDecimal discount = BigDecimal.valueOf(DISCOUNT_START_PRICE);
        BigDecimal untilChristmas = new BigDecimal(DISCOUNT_DAY_UNIT).multiply(new BigDecimal(visitDay.diffFromFirstDay()));
        return discount.add(untilChristmas);
    }

    public BigDecimal checkDayDiscount() {
        if (visitDay.isWeekend()) {
            return calculateMainMenuDiscount();
        }
        return calculateDessertMenu();
    }

    private BigDecimal calculateMainMenuDiscount() {
        return new BigDecimal(DISCOUNT_PER_MENU).multiply(new BigDecimal(orders.countSameTypeMenu(MenuType.MAIN)));
    }

    private BigDecimal calculateDessertMenu() {
        return new BigDecimal(DISCOUNT_PER_MENU).multiply(new BigDecimal(orders.countSameTypeMenu(MenuType.DESSERT)));
    }

    public BigDecimal checkSpecialDiscount() {
        if (visitDay.isSpecialDay()) {
            return BigDecimal.valueOf(DISCOUNT_START_PRICE);
        }
        return BigDecimal.ZERO;
    }

    public Optional<Order> checkGift() {
        if (orders.calculateTotalPrice().compareTo(new BigDecimal(MIN_GIFT_PRICE)) >= 0) {
            return Optional.of(new Order(Menu.샴페인, new Quantity(1)));
        }
        return Optional.empty();
    }

    public BigDecimal calculateExpectPrice(BigDecimal totalDiscountPrice) {
        return orders.calculateTotalPrice().subtract(totalDiscountPrice);
    }

    public BigDecimal makeGiftDiscount(final Optional<Order> optionalOrder) {
        if (optionalOrder.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return optionalOrder.get().getPrice();
    }
}
