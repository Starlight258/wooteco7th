package christmas;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ApplicationTest extends NsTest {

    private static final String LINE_SEPARATOR = System.lineSeparator();

    @Test
    void 모든_타이틀_출력() {
        assertSimpleTest(() -> {
            run("3", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1");
            assertThat(output()).contains(
                    "<주문 메뉴>",
                    "<할인 전 총주문 금액>",
                    "<증정 메뉴>",
                    "<혜택 내역>",
                    "<총혜택 금액>",
                    "<할인 후 예상 결제 금액>",
                    "<12월 이벤트 배지>"
            );
        });
    }

    @Test
    void 혜택_내역_없음_출력() {
        assertSimpleTest(() -> {
            run("26", "타파스-1,제로콜라-1");
            assertThat(output()).contains("<혜택 내역>" + LINE_SEPARATOR + "없음");
        });
    }

    @Test
    void 날짜_예외_테스트() {
        assertSimpleTest(() -> {
            runException("a");
            assertThat(output()).contains("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        });
    }

    @Test
    void 주문_예외_테스트() {
        assertSimpleTest(() -> {
            runException("3", "제로콜라-a");
            assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        });
    }

    @DisplayName("날짜 입력 엣지 케이스")
    @ParameterizedTest
    @ValueSource(strings = {"0", "32", "-1", "a", "1.5"})
    void validateDateInput(String input) {
        assertSimpleTest(() -> {
            runException(input);
            assertThat(output()).contains("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        });
    }

    @DisplayName("주문 메뉴 입력 엣지 케이스")
    @ParameterizedTest
    @ValueSource(strings = {
            "제로콜라-0",                          // 0개 주문
            "제로콜라-21",                         // 20개 초과 주문
            "없는메뉴-1",                          // 존재하지 않는 메뉴
            "제로콜라-1,제로콜라-1",               // 중복 메뉴
            "제로콜라-1,레드와인-1",               // 음료만 주문
            "시저샐러드-1,,제로콜라-1",            // 잘못된 형식 (더블 콤마)
            "시저샐러드-1,제로콜라-a",             // 잘못된 수량 형식
            "-시저샐러드-1",                       // 잘못된 메뉴 형식
            "시저샐러드-1-1"                       // 잘못된 구분자
    })
    void validateOrderInput(String input) {
        assertSimpleTest(() -> {
            runException("3", input);
            assertThat(output()).contains("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
        });
    }

    @DisplayName("특별 할인 날짜 테스트 (일요일, 크리스마스)")
    @ParameterizedTest
    @ValueSource(strings = {"3", "10", "17", "24", "25", "31"})
        // 일요일들과 크리스마스
    void specialDiscountDays(String date) {
        assertSimpleTest(() -> {
            run(date, "티본스테이크-1");
            assertThat(output()).contains("특별 할인: -1,000원");
        });
    }

    @DisplayName("크리스마스 디데이 할인 경계값 테스트")
    @Test
    void christmasDiscountBoundary() {
        assertSimpleTest(() -> {
            run("1", "티본스테이크-1");  // 첫날
            assertThat(output()).contains("크리스마스 디데이 할인: -1,000원");
        });

        assertSimpleTest(() -> {
            run("25", "티본스테이크-1");  // 마지막날
            assertThat(output()).contains("크리스마스 디데이 할인: -3,400원");
        });

        assertSimpleTest(() -> {
            run("26", "티본스테이크-1");  // 이벤트 종료
            assertThat(output()).contains("<혜택 내역>\n없음");
        });
    }

    @DisplayName("이벤트 배지 경계값 테스트")
    @Test
    void badgeBoundaryTest() {
        // 4,223원 혜택 (배지 없음)
        // 크리스마스 디데이 할인: -1,200원
        // 평일 할인: -2,023원
        // 특별 할인: -1,000원
        assertSimpleTest(() -> {
            run("3", "아이스크림-1");  // 5,000원
            assertThat(output()).contains("<12월 이벤트 배지>" + LINE_SEPARATOR + "없음");
        });

        // 6,246원 혜택 (별)
        // 크리스마스 디데이 할인: -1,200원
        // 평일 할인: -4,046원
        // 특별 할인: -1,000원
        assertSimpleTest(() -> {
            run("3", "아이스크림-2");  // 10,000원
            assertThat(output()).contains("<12월 이벤트 배지>" + LINE_SEPARATOR + "별");
        });

        // 8,269원 혜택 (별)
        // 크리스마스 디데이 할인: -1,200원
        // 평일 할인: -6,069원
        // 특별 할인: -1,000원
        assertSimpleTest(() -> {
            run("3", "초코케이크-3");  // 45,000원
            assertThat(output()).contains("<12월 이벤트 배지>" + LINE_SEPARATOR + "별");
        });

        // 29,223원 혜택 (산타)
        // 크리스마스 디데이 할인: -1,200원
        // 평일 할인: -2,023원
        // 특별 할인: -1,000원
        // 증정 이벤트: -25,000원
        assertSimpleTest(() -> {
            run("3", "티본스테이크-3");  // 110,000원
            assertThat(output()).contains("<12월 이벤트 배지>" + LINE_SEPARATOR + "산타");
        });
    }

    @DisplayName("최소 주문 금액 테스트")
    @Test
    void minimumOrderAmountTest() {
        assertSimpleTest(() -> {
            run("3", "아이스크림-1");  // 5,000원 (혜택 적용 안됨)
            assertThat(output()).contains("<혜택 내역>" + LINE_SEPARATOR + "없음");
        });

        assertSimpleTest(() -> {
            run("3", "아이스크림-2");  // 10,000원 (혜택 적용)
            assertThat(output()).contains("평일 할인: -4,046원");
        });
    }


    @Override
    protected void runMain() {
        Application.main(new String[]{});
    }
}
