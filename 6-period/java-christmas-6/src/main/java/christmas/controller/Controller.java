package christmas.controller;

import christmas.domain.Day;
import christmas.domain.Order;
import christmas.domain.Orders;
import christmas.domain.PromotionProcessor;
import christmas.dto.OrderMenuDto;
import christmas.exception.ExceptionHandler;
import christmas.service.Service;
import christmas.support.StringFormatter;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Controller {

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
        outputView.informWelcome();
        Day visitDay = createDay();
        Orders orders = createOrders();
        showOrders(orders, visitDay);
        checkPromotion(visitDay, orders);
    }

    private void checkPromotion(final Day visitDay, final Orders orders) {
        PromotionProcessor promotionProcessor = new PromotionProcessor(visitDay, orders);
        Optional<Order> optionalOrder = promotionProcessor.checkGift();
        outputView.informBonusMenu();
        outputView.show(stringFormatter.makeOptionalOrderMessage(optionalOrder));
        outputView.informBenefitDetails();
        showDiscount(visitDay, promotionProcessor, optionalOrder);
    }

    private void showDiscount(final Day visitDay, final PromotionProcessor promotionProcessor,
                              final Optional<Order> optionalOrder) {
        boolean noPromotion = !promotionProcessor.checkAtLeastPrice();
        BigDecimal untilChristmasDiscount = promotionProcessor.checkUntilChristmasDiscount();
        BigDecimal dayDiscount = promotionProcessor.checkDayDiscount();
        BigDecimal specialDiscount = promotionProcessor.checkSpecialDiscount();
        BigDecimal giftDiscount = promotionProcessor.makeGiftDiscount(optionalOrder);
        outputView.show(
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
        outputView.show(stringFormatter.makeTotalPriceMessage(totalDiscount, expectPrice, noPromotion));
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
        outputView.requestOrderMenu();
        return exceptionHandler.retryOn(() -> {
            List<String> orderInputs = inputView.readMenu();
            return service.createOrders(orderInputs);
        });
    }

    private Day createDay() {
        return exceptionHandler.retryOn(() -> {
            outputView.requestVisitDay();
            int day = inputView.readDay();
            return service.createDay(day);
        });
    }


    private void showOrders(final Orders orders, final Day day) {
        outputView.informPreview(day.getValue());
        showOrderMenus(orders);
        outputView.informDiscountPrice(orders.calculateTotalPrice());
    }

    private void showOrderMenus(final Orders orders) {
        List<OrderMenuDto> orderMenuDtos = service.toOrderMenuDto(orders);
        outputView.informOrderMenu(orderMenuDtos);
    }
}
