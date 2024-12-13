package calculator;

import calculator.controller.CalculatorController;
import calculator.view.InputView;
import calculator.view.OutputView;
import camp.nextstep.edu.missionutils.Console;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        CalculatorController calculatorController = new CalculatorController(inputView, outputView);
        try {
            calculatorController.process();
        } finally {
            Console.close();
        }
    }
}
