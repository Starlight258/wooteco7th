package calculator.view;

public class OutputView {

    private static final String LINE = System.lineSeparator();
    private static final String REQUEST_TEXT = "덧셈할 문자열을 입력해 주세요.";
    private static final String INFORM_RESULT = "결과 : %d";

    public void showRequestText() {
        showln(REQUEST_TEXT);
    }

    public void showInformResult() {
        showln(INFORM_RESULT);
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
