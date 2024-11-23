package christmas;

import camp.nextstep.edu.missionutils.Console;
import christmas.controller.Controller;
import christmas.exception.ExceptionHandler;
import christmas.support.StringFormatter;
import christmas.view.InputView;
import christmas.view.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        StringFormatter stringFormatter = new StringFormatter();
        ExceptionHandler exceptionHandler = new ExceptionHandler(outputView);
        Controller controller = new Controller(inputView, outputView, stringFormatter, exceptionHandler);
        try {
            controller.process();
        } finally {
            Console.close();
        }
    }
}
