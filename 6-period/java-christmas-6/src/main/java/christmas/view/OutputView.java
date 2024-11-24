package christmas.view;

import christmas.dto.OrderMenuDto;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class OutputView {

    private static final String LINE = System.lineSeparator();

    private static final String INFORM_WELCOME = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String REQUEST_VISIT_DAY = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    private static final String REQUEST_ORDER_MENU = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)  ";
    private static final String INFORM_PREVIEW = "12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!";

    private static final String INFORM_ORDER_MENU = "<주문 메뉴>";
    private static final String INFORM_DISCOUNT_PRICE = "<할인 전 총주문 금액>";
    private static final String INFORM_BONUS_MENU = "<증정 메뉴>";
    private static final String INFORM_BENEFIT_DETAILS = "<혜택 내역>";

    private static final String FORMAT_MENU_COUNT = "%s %d개";
    public static final String FORMAT_PRICE = "%,.0f원";

    public void informWelcome() {
        System.out.println(INFORM_WELCOME);
    }

    public void requestVisitDay() {
        System.out.println(REQUEST_VISIT_DAY);
    }

    public void requestOrderMenu() {
        System.out.println(REQUEST_ORDER_MENU);
    }

    public void informPreview(int day) {
        String message = String.format(INFORM_PREVIEW, day);
        show(message + LINE + LINE);
    }

    public void informOrderMenu(final List<OrderMenuDto> orderMenuDtos) {
        String message = orderMenuDtos.stream()
                .map(dto -> String.format(FORMAT_MENU_COUNT, dto.name(), dto.quantity()))
                .collect(Collectors.joining(LINE));
        show(INFORM_ORDER_MENU + LINE + message + LINE);
    }

    public void informDiscountPrice(final BigDecimal price) {
        String message = String.format(FORMAT_PRICE, price);
        show(INFORM_DISCOUNT_PRICE + LINE + message + LINE);
    }

    public void informBonusMenu() {
        System.out.println(INFORM_BONUS_MENU);
    }

    public void informBenefitDetails() {
        System.out.println(INFORM_BENEFIT_DETAILS);
    }

    public void showException(Exception exception) {
        System.out.println(exception.getMessage());
    }

    public void show(String message) {
        System.out.println(message);
    }
}
