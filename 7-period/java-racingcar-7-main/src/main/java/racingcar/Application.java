package racingcar;

import camp.nextstep.edu.missionutils.Console;
import racingcar.controller.RacingController;
import racingcar.generator.NumberGenerator;
import racingcar.generator.RandomNumberGenerator;
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
        NumberGenerator numberGenerator = new RandomNumberGenerator();
        return new RacingController(inputView, outputView,
                numberGenerator);
    }
}
