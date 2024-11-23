package christmas.view;

import java.math.BigDecimal;

public class OutputView {

    private static final String COMMENT_WELCOME_MESSAGE = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String COMMENT_VISIT_DATE = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    private static final String COMMENT_ORDER_MENU = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)  ";
    private static final String COMMENT_EVENT = "12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n";
    private static final String ORDER_MENU = "<주문 메뉴>";
    private static final String TOTAL_ORDER_BEFORE_DISCOUNT = "<할인 전 총주문 금액>";
    private static final String BONUS_MENU = "<증정 메뉴>";
    private static final String PROMOTION_LIST = "<혜택 내역>";

    public void commentWelcomeMessage() {
        System.out.println(COMMENT_WELCOME_MESSAGE);
    }

    public void commentVisitDate() {
        System.out.println(COMMENT_VISIT_DATE);
    }

    public void commentOrderMenu() {
        System.out.println(COMMENT_ORDER_MENU);
    }

    public void commentEvent(int day) {
        System.out.printf(String.format(COMMENT_EVENT, day));
        System.out.println();
    }

    public void showOrderMenu() {
        System.out.println(ORDER_MENU);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showOrderPrice(final BigDecimal price) {
        System.out.println(TOTAL_ORDER_BEFORE_DISCOUNT);
        System.out.printf("%,.0f원", price);
        System.out.println();
    }

    public void showBonusMenu() {
        System.out.println(BONUS_MENU);
    }

    public void showPromotionList() {
        System.out.println(PROMOTION_LIST);
    }

    public void showBlankLine() {
        System.out.println();
    }

    public void showException(Exception exception) {
        System.out.println(exception.getMessage());
    }
}
