package calculator.domain;

public class NumberParser {

    private final String text;

    public NumberParser(final String text) {
        this.text = text;
    }

    public void parse() {
        // 커스텀 구분자 선언 확인
        findCustomDelimiter();
        // 기본 구분자 + 커스텀 구분자로 분리
    }

    private void findCustomDelimiter() {

    }
}
