package christmas.domain.gift;

import christmas.domain.Day;
import christmas.domain.Menu;
import christmas.domain.Orders;
import christmas.domain.Quantity;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

public class BonusGift implements Gift {

    private static final int MIN_ORDER_PRICE = 120_000;
    private static final String BONUS_EVENT = "증정 이벤트";

    private final Day day;
    private final Orders orders;

    public BonusGift(final Day day, final Orders orders) {
        this.day = day;
        this.orders = orders;
    }

    @Override
    public boolean isApplicable() {
        return orders.isTotalPriceOverThan(BigDecimal.valueOf(MIN_ORDER_PRICE)) && day.isInDecember();
    }

    @Override
    public BigDecimal calculateAmount() {
        BigDecimal price = BigDecimal.ZERO;
        if (isApplicable()) {
            for (Entry<Menu, Quantity> entry : provideGiftItems().entrySet()) {
                price = price.add(entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue().getValue())));
            }
        }
        return price;
    }

    @Override
    public Map<Menu, Quantity> provideGiftItems() {
        if (isApplicable()) {
            return Map.of(Menu.샴페인, new Quantity(1));
        }
        return Collections.emptyMap();
    }

    @Override
    public String getName() {
        return BONUS_EVENT;
    }
}
