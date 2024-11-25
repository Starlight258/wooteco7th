package christmas.domain.discount;

import christmas.domain.Day;
import christmas.domain.Orders;
import java.math.BigDecimal;

public abstract class Discount {

    private static final int MIN_AMOUNT = 10_000;

    protected final Day day;
    protected final Orders orders;

    public Discount(final Day day, final Orders orders) {
        this.day = day;
        this.orders = orders;
    }

    public boolean isApplicable() {
        return isMoreThanMinimumOrderAmount(orders) && isWithinDeadline();
    }

    public abstract BigDecimal calculateAmount();

    public abstract String getName();

    protected abstract boolean isWithinDeadline();

    private boolean isMoreThanMinimumOrderAmount(Orders orders) {
        return orders.calculateTotalPrice().compareTo(new BigDecimal(MIN_AMOUNT)) >= 0;
    }
}
