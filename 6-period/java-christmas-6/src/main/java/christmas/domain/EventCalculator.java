package christmas.domain;

import christmas.domain.discount.Discount;
import christmas.domain.gift.Gift;
import java.math.BigDecimal;
import java.util.List;

public class EventCalculator {

    private final List<Discount> discounts;
    private final List<Gift> bonuses;

    public EventCalculator(final List<Discount> discounts, final List<Gift> bonuses) {
        this.discounts = discounts;
        this.bonuses = bonuses;
    }

    public BigDecimal calculateTotalBenefitPrice() {
        BigDecimal price = BigDecimal.ZERO;
        price = price.add(sumDiscountPrice());
        return price.add(bonuses.stream()
                .map(Gift::calculateAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    private BigDecimal sumDiscountPrice() {
        return discounts.stream()
                .map(Discount::calculateAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateEstimatedPrice(final Orders orders) {
        BigDecimal price = orders.calculateTotalPrice();
        return price.subtract(sumDiscountPrice());
    }

    public String getBadgeName() {
        BigDecimal totalBenefitPrice = calculateTotalBenefitPrice();
        return Badge.showName(totalBenefitPrice);
    }
}
