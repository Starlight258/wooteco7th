package christmas.service;

import christmas.domain.Menu;
import christmas.domain.Order;
import christmas.domain.Orders;
import christmas.domain.Quantity;
import christmas.exception.CustomIllegalArgumentException;
import christmas.exception.ErrorMessage;
import christmas.util.Converter;
import christmas.util.StringParser;
import java.util.List;
import java.util.regex.Pattern;

public class Service {

    private static final Pattern PATTERN = Pattern.compile("^([가-힣]+)-(\\d+)$");

    public Orders createOrders(final List<String> orderInputs) {
        return new Orders(orderInputs.stream()
                .map(this::createOrder)
                .toList());
    }

    private Order createOrder(final String order) {
        checkSuitablePattern(order);
        List<String> splitted = StringParser.extractByGroup(order, PATTERN);
        Menu menu = Menu.from(splitted.get(0));
        Quantity quantity = new Quantity(
                Converter.convertToInteger(splitted.get(1), ErrorMessage.INVALID_DAY));
        return new Order(menu, quantity);
    }

    private void checkSuitablePattern(final String order) {
        if (StringParser.isNotSuitablePattern(order, PATTERN)) {
            throw new CustomIllegalArgumentException(ErrorMessage.INVALID_ORDER);
        }
    }
}
