package racingcar.view;

public class OutputView {

    private static final String LINE = System.lineSeparator();

    public static final String REQUEST_NAME = "경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)";
    public static final String REQUEST_ATTEMPT = "시도할 횟수는 몇 회인가요?";
    public static final String INFORM_RESULT = "실행 결과";
    public static final String INFORM_CAR_POSITION = "%s : %s";
    public static final String INFORM_WINNER = "최종 우승자 : %s";

    // TITLE_ : 제목
    // REQUEST_ : 요청
    // INFORM_ : 결과

    public void showRequestName() {
        showln(REQUEST_NAME);
    }

    public void showRequestAttempt(){
        showln(REQUEST_ATTEMPT);
    }

    public void showInformResult(){
        showln(INFORM_RESULT);
    }

    public void showCarPosition(){
        showln(INFORM_CAR_POSITION);
    }

    public void showException(Exception exception) {
        showln(exception.getMessage());
    }

    private String format(String format, Object... args) {
        return String.format(format, args);
    }

    private void showln(String message) {
        System.out.println(message);
    }
}
