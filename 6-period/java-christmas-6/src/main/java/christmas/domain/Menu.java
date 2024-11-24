package christmas.domain;

import christmas.exception.CustomIllegalArgumentException;
import christmas.exception.ErrorMessage;
import java.math.BigDecimal;
import java.util.Arrays;

public enum Menu {

    양송이수프(new BigDecimal(6000), MenuType.APPETIZER),
    타파스(new BigDecimal(5500), MenuType.APPETIZER),
    시저샐러드(new BigDecimal(8000), MenuType.APPETIZER),

    티본스테이크(new BigDecimal(55000), MenuType.MAIN),
    바비큐립(new BigDecimal(54000), MenuType.MAIN),
    해산물파스타(new BigDecimal(35000), MenuType.MAIN),
    크리스마스파스타(new BigDecimal(25000), MenuType.MAIN),

    초코케이크(new BigDecimal(15000), MenuType.DESSERT),
    아이스크림(new BigDecimal(5000), MenuType.DESSERT),

    제로콜라(new BigDecimal(3000), MenuType.DRINK),
    레드와인(new BigDecimal(60000), MenuType.DRINK),
    샴페인(new BigDecimal(25000), MenuType.DRINK),

    없음(BigDecimal.ZERO, MenuType.NONE);

    private final BigDecimal price;
    private final MenuType type;

    Menu(final BigDecimal price, final MenuType type) {
        this.price = price;
        this.type = type;
    }

    public static Menu from(String name) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new CustomIllegalArgumentException(ErrorMessage.INVALID_ORDER));
    }

    public enum MenuType {
        APPETIZER, MAIN, DESSERT, DRINK, NONE;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public MenuType getType() {
        return type;
    }
}
