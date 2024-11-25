package christmas;

import camp.nextstep.edu.missionutils.Console;
import christmas.controller.Controller;
import christmas.exception.ExceptionHandler;
import christmas.service.Service;
import christmas.view.InputView;
import christmas.view.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        ExceptionHandler exceptionHandler = new ExceptionHandler(outputView);
        Service service = new Service();
        Controller controller = new Controller(inputView, outputView, exceptionHandler, service);
        try {
            controller.process();
        } finally {
            Console.close();
        }
    }
}
