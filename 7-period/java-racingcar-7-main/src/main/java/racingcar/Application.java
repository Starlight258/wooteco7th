package racingcar;

import camp.nextstep.edu.missionutils.Console;
import racingcar.controller.RacingController;
import racingcar.exception.ExceptionHandler;
import racingcar.service.RacingService;
import racingcar.view.InputView;
import racingcar.view.OutputView;

public class Application {
    public static void main(String[] args) {
        final RacingController controller = makeController();
        try {
            controller.process();
        } finally {
            Console.close();
        }
    }

    private static RacingController makeController() {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        ExceptionHandler exceptionHandler = new ExceptionHandler(outputView);
        RacingService racingService = new RacingService();
        RacingController controller = new RacingController(inputView, outputView, exceptionHandler, racingService);
        return controller;
    }
}
