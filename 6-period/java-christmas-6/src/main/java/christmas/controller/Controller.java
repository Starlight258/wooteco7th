package christmas.controller;

import christmas.domain.Day;
import christmas.domain.Order;
import christmas.domain.Orders;
import christmas.domain.PromotionProcessor;
import christmas.exception.ExceptionHandler;
import christmas.service.Service;
import christmas.support.StringFormatter;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Controller {

    private static final int YEAR = 2023;
    private static final int DECEMBER = 12;

    private final InputView inputView;
    private final OutputView outputView;
    private final StringFormatter stringFormatter;
    private final ExceptionHandler exceptionHandler;
    private final Service service;

    public Controller(final InputView inputView, final OutputView outputView, final StringFormatter stringFormatter,
                      final ExceptionHandler exceptionHandler, final Service service) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.stringFormatter = stringFormatter;
        this.exceptionHandler = exceptionHandler;
        this.service = service;
    }

    public void process() {
        outputView.commentWelcomeMessage();
        Day visitDay = createDay();
        Orders orders = createOrders();
        showOrders(orders, visitDay);
        checkPromotion(visitDay, orders);
    }

    private void checkPromotion(final Day visitDay, final Orders orders) {
        PromotionProcessor promotionProcessor = new PromotionProcessor(visitDay, orders);
        Optional<Order> optionalOrder = promotionProcessor.checkGift();
        outputView.showBonusMenu();
        outputView.showMessage(stringFormatter.makeOptionalOrderMessage(optionalOrder));
        outputView.showPromotionList();
        showDiscount(visitDay, promotionProcessor, optionalOrder);
    }

    private void showDiscount(final Day visitDay, final PromotionProcessor promotionProcessor,
                              final Optional<Order> optionalOrder) {
        boolean noPromotion = !promotionProcessor.checkAtLeastPrice();
        BigDecimal untilChristmasDiscount = promotionProcessor.checkUntilChristmasDiscount();
        BigDecimal dayDiscount = promotionProcessor.checkDayDiscount();
        BigDecimal specialDiscount = promotionProcessor.checkSpecialDiscount();
        BigDecimal giftDiscount = promotionProcessor.makeGiftDiscount(optionalOrder);
        outputView.showMessage(
                stringFormatter.makePromotionListMessage(untilChristmasDiscount, dayDiscount, specialDiscount,
                        giftDiscount, visitDay, noPromotion));
        showTotalDiscount(promotionProcessor, noPromotion, untilChristmasDiscount, dayDiscount, specialDiscount,
                giftDiscount);
    }

    private void showTotalDiscount(final PromotionProcessor promotionProcessor, final boolean noPromotion,
                                   final BigDecimal untilChristmasDiscount, final BigDecimal dayDiscount,
                                   final BigDecimal specialDiscount, final BigDecimal giftDiscount) {
        BigDecimal totalDiscount = addTotalDiscount(untilChristmasDiscount, dayDiscount, specialDiscount, giftDiscount,
                noPromotion);
        BigDecimal discountPriceExceptBonusPrice = totalDiscount.subtract(giftDiscount);
        BigDecimal expectPrice = promotionProcessor.calculateExpectPrice(discountPriceExceptBonusPrice);
        outputView.showMessage(stringFormatter.makeTotalPriceMessage(totalDiscount, expectPrice, noPromotion));
    }

    private BigDecimal addTotalDiscount(final BigDecimal untilChristmasDiscount, final BigDecimal dayDiscount,
                                        final BigDecimal specialDiscount, final BigDecimal giftDiscount,
                                        final boolean noPromotion) {
        if (noPromotion) {
            return BigDecimal.ZERO;
        }
        BigDecimal totalDiscount = untilChristmasDiscount;
        totalDiscount = totalDiscount.add(dayDiscount);
        totalDiscount = totalDiscount.add(specialDiscount);
        totalDiscount = totalDiscount.add(giftDiscount);
        return totalDiscount;
    }

    private Orders createOrders() {
        outputView.commentOrderMenu();
        return exceptionHandler.retryOn(() -> {
            List<String> orderInputs = inputView.readMenu();
            return service.createOrders(orderInputs);
        });
    }

    private Day createDay() {
        return exceptionHandler.retryOn(() -> {
            outputView.commentVisitDate();
            int day = inputView.readDay();
            return new Day(YEAR, DECEMBER, day);
        });
    }


    private void showOrders(final Orders orders, final Day day) {
        outputView.commentEvent(day.getValue());
        showOrderMenus(orders);
        outputView.showOrderPrice(orders.calculateTotalPrice());
        outputView.showBlankLine();
    }

    private void showOrderMenus(final Orders orders) {
        outputView.showOrderMenu();
        outputView.showMessage(stringFormatter.makeOptionalOrderMessage(orders));
    }
}
