package calculator.controller;

import calculator.domain.PositiveCalculator;
import calculator.domain.NumberParser;
import calculator.view.InputView;
import calculator.view.OutputView;
import java.util.List;

public class CalculatorController {

    private final InputView inputView;
    private final OutputView outputView;

    public CalculatorController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void process() {
        String text = readText();
        List<Integer> tokens = parse(text);
        showTotal(tokens);
    }

    private List<Integer> parse(final String text) {
        NumberParser numberParser = new NumberParser(text);
        return numberParser.parse();
    }

    private String readText() {
        outputView.showRequestText();
        return inputView.readText();
    }

    private void showTotal(final List<Integer> tokens) {
        PositiveCalculator positiveCalculator = new PositiveCalculator();
        int total = positiveCalculator.add(tokens);
        outputView.showInformResult(total);
    }

}
