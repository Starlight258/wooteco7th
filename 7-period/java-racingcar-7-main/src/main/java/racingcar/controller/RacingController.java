package racingcar.controller;

import java.util.List;
import racingcar.domain.Attempt;
import racingcar.domain.Car;
import racingcar.domain.CarNames;
import racingcar.domain.Cars;
import racingcar.domain.RacingGame;
import racingcar.dto.CarNameDto;
import racingcar.dto.TotalCarPositionDto;
import racingcar.generator.NumberGenerator;
import racingcar.view.InputView;
import racingcar.view.OutputView;

public class RacingController {

    private final InputView inputView;
    private final OutputView outputView;
    private final NumberGenerator numberGenerator;

    public RacingController(final InputView inputView, final OutputView outputView,
                            final NumberGenerator numberGenerator) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.numberGenerator = numberGenerator;
    }

    public void process() {
        Cars cars = makeCars();
        Attempt attempt = makeAttempt();
        RacingGame game = new RacingGame(cars, attempt);
        processGame(game);
    }

    private void processGame(final RacingGame game) {
        processRacing(game);
        showResult(game);
    }

    private void showResult(final RacingGame game) {
        CarNameDto carNameDto = CarNameDto.of(game.calculateWinners());
        outputView.showWinnerResult(carNameDto.names());
    }

    private void processRacing(final RacingGame game) {
        outputView.showInformResult();
        while (shouldContinue(game)) {
            List<Car> racingCars = game.race();
            outputView.makeCarPosition(TotalCarPositionDto.of(racingCars));
        }
    }

    private boolean shouldContinue(final RacingGame game) {
        return !game.isEnd();
    }

    private Cars makeCars() {
        CarNames carNames = makeCarNames();
        return Cars.of(carNames.getNames(), numberGenerator);
    }

    private Attempt makeAttempt() {
        outputView.showRequestAttempt();
        return new Attempt(inputView.readRequestAttempt());
    }

    private CarNames makeCarNames() {
        outputView.showRequestName();
        return CarNames.of(inputView.readRequestName());
    }
}
