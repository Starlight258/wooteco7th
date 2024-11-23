package christmas.domain;

import christmas.domain.Menu.MenuType;
import java.math.BigDecimal;
import java.util.Optional;

public class PromotionProcessor {

    private final Day visitDay;
    private final Orders orders;

    public PromotionProcessor(final Day visitDay, final Orders orders) {
        this.visitDay = visitDay;
        this.orders = orders;
    }

    public boolean checkAtLeastPrice() {
        return orders.calculateTotalPrice().compareTo(new BigDecimal(10000)) >= 0;
    }

    public BigDecimal checkUntiChristmasDiscount() {
        BigDecimal discount = BigDecimal.valueOf(1000);
        BigDecimal untilChristmas = new BigDecimal(100).multiply(new BigDecimal(visitDay.diffFromFirstDay()));
        return discount.add(untilChristmas);
    }

    public BigDecimal checkDayDiscount() {
        if (visitDay.isWeekend()) {
            return calculateMainMenuDiscount();
        }
        return calculateDessertMenu();
    }

    private BigDecimal calculateMainMenuDiscount() {
        return new BigDecimal(2023).multiply(new BigDecimal(orders.countSameTypeMenu(MenuType.MAIN)));
    }

    private BigDecimal calculateDessertMenu() {
        return new BigDecimal(2023).multiply(new BigDecimal(orders.countSameTypeMenu(MenuType.DESSERT)));
    }

    public BigDecimal checkSpecialDiscount() {
        if (visitDay.isSpecialDay()) {
            return BigDecimal.valueOf(1000);
        }
        return BigDecimal.ZERO;
    }

    public Optional<Order> checkGift() {
        if (orders.calculateTotalPrice().compareTo(new BigDecimal("120000")) >= 0) {
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
