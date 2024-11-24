package christmas.service;

import christmas.domain.Day;
import christmas.domain.Menu;
import christmas.domain.Order;
import christmas.domain.Orders;
import christmas.domain.Quantity;
import christmas.dto.OrderMenuDto;
import christmas.exception.CustomIllegalArgumentException;
import christmas.exception.ErrorMessage;
import christmas.util.Converter;
import christmas.util.StringParser;
import java.util.List;
import java.util.regex.Pattern;

public class Service {

    private static final Pattern PATTERN = Pattern.compile("^([가-힣]+)-(\\d+)$");
    private static final int YEAR = 2023;
    private static final int DECEMBER = 12;

    public Orders createOrders(final List<String> orderInputs) {
        return new Orders(orderInputs.stream()
                .map(this::createOrder)
                .toList());
    }

    public Day createDay(final int day) {
        return new Day(YEAR, DECEMBER, day);
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

    public List<OrderMenuDto> toOrderMenuDto(final Orders orders) {
        return orders.getOrders().stream()
                .map(OrderMenuDto::of)
                .toList();
    }
}
