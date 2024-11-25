package christmas.domain.discount;

import christmas.domain.Day;
import christmas.domain.Orders;
import java.math.BigDecimal;

public class SpecialDiscount extends Discount {

    private static final int MIN_ORDER_PRICE = 1_000;
    private static final String SPECIAL_DISCOUNT = "특별 할인";

    public SpecialDiscount(final Day day, final Orders orders) {
        super(day, orders);
    }

    @Override
    public BigDecimal calculateAmount() {
        if (isApplicable()) {
            return BigDecimal.valueOf(MIN_ORDER_PRICE);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public String getName() {
        return SPECIAL_DISCOUNT;
    }

    @Override
    protected boolean isWithinDeadline() {
        return day.isSpecialDay();
    }
}
