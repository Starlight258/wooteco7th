package racingcar.view;

import java.util.List;
import racingcar.dto.TotalCarPositionDto;

public class OutputView {

    private static final String LINE = System.lineSeparator();

    private static final String REQUEST_NAME = "경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)";
    private static final String REQUEST_ATTEMPT = "시도할 횟수는 몇 회인가요?";
    private static final String INFORM_RESULT = "실행 결과";
    private static final String INFORM_CAR_POSITION = "%s : %s";
    private static final String INFORM_WINNER = "최종 우승자 : %s";
    private static final String DELIMITER = ", ";
    private static final String HYPEN = "-";

    public void showRequestName() {
        showln(REQUEST_NAME);
    }

    public void showRequestAttempt() {
        showln(REQUEST_ATTEMPT);
    }

    public void showInformResult() {
        showln(LINE + INFORM_RESULT);
    }

    public void makeCarPosition(final TotalCarPositionDto input) {
        input.dtos().stream()
                .map(dto -> format(INFORM_CAR_POSITION, dto.name(), makePositionMessage(dto.position())))
                .forEach(this::showln);
        showln("");
    }

    public String makePositionMessage(final int position) {
        return HYPEN.repeat(position);
    }

    public void showWinnerResult(final List<String> names) {
        showln(format(INFORM_WINNER, makeWinnersMessage(names)));
    }

    private String makeWinnersMessage(final List<String> winners) {
        return String.join(DELIMITER, winners);
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
