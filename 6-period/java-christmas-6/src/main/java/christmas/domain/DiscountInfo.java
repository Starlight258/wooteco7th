package christmas.domain;


import christmas.domain.vo.OrderMenus;
import christmas.domain.vo.VisitDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscountInfo {
    private final OrderMenus orderMenus;
    private final VisitDate visitDate;
    private final int priceBeforeDiscount;
    private Map<Discount, Integer> discountResults;
    private final Promotion promotion;
    private final int totalDiscountAmount;
    private final Badge badge;

    public DiscountInfo(final OrderMenus orderMenus, final VisitDate visitDate, final int priceBeforeDiscount,
                        final Map<Discount, Integer> discountResults, final Promotion promotion,
                        final int totalDiscountAmount, final Badge badge) {
        this.orderMenus = orderMenus;
        this.visitDate = visitDate;
        this.priceBeforeDiscount = priceBeforeDiscount;
        this.discountResults = discountResults;
        this.promotion = promotion;
        this.totalDiscountAmount = totalDiscountAmount;
        this.badge = badge;
    }

    public static DiscountInfo initialize(final OrderMenus orderMenus, final VisitDate visitDate) {
        return new DiscountInfo(
                orderMenus,
                visitDate,
                calculatePriceBeforeDiscount(orderMenus),
                new HashMap<>(),
                Promotion.NONE,
                0,
                Badge.NONE
        );
    }

    public int getFinalPrice() {
        return priceBeforeDiscount - totalDiscountAmount;
    }

    public DiscountInfo updateDiscountResults(List<DiscountResult> discountResults) {
        return new DiscountInfo(
                this.orderMenus,
                this.visitDate,
                this.priceBeforeDiscount,
                this.discountResults = calculateDiscountResults(discountResults),
                this.promotion,
                calculateTotalDiscountAmount(),
                this.badge
        );
    }

    public DiscountInfo updatePromotion(Promotion promotion) {
        return new DiscountInfo(
                this.orderMenus,
                this.visitDate,
                this.priceBeforeDiscount,
                this.discountResults,
                promotion,
                this.totalDiscountAmount,
                this.badge
        );
    }

    public DiscountInfo updateBadge(Badge badge) {
        return new DiscountInfo(
                this.orderMenus,
                this.visitDate,
                this.priceBeforeDiscount,
                this.discountResults,
                this.promotion,
                this.totalDiscountAmount,
                badge
        );
    }

    public OrderMenus getOrderMenus() {
        return orderMenus;
    }

    public VisitDate getVisitDate() {
        return visitDate;
    }

    public int getPriceBeforeDiscount() {
        return priceBeforeDiscount;
    }

    public Map<Discount, Integer> getDiscountResults() {
        return discountResults;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public int getTotalDiscountAmount() {
        return totalDiscountAmount;
    }

    public Badge getBadge() {
        return badge;
    }

    private Map<Discount, Integer> calculateDiscountResults(final List<DiscountResult> discountResults) {
        for (DiscountResult discountResult : discountResults) {
            this.discountResults.merge(discountResult.getDiscount(), discountResult.getAmount(), Integer::sum);
        }
        return this.discountResults;
    }

    private int calculateTotalDiscountAmount() {
        return discountResults.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    private static int calculatePriceBeforeDiscount(final OrderMenus orderMenus) {
        return orderMenus.calculatePriceBeforeDiscount();
    }


}
