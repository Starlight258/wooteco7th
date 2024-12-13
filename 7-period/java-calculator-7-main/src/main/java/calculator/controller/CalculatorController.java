package calculator.controller;

import calculator.view.InputView;
import calculator.view.OutputView;

public class CalculatorController {

    private final InputView inputView;
    private final OutputView outputView;

    public CalculatorController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void process() {
        // 덧셈할 문자열 입력 기능 구현
        String text = inputView.readText();

    }

}
