package racingcar.controller;

import java.util.List;
import racingcar.domain.CarNames;
import racingcar.dto.CarsPositionDto;
import racingcar.dto.CarsPositionDto.CarPositionDto;
import racingcar.exception.ExceptionHandler;
import racingcar.service.RacingService;
import racingcar.view.InputView;
import racingcar.view.OutputView;

public class RacingController {

    private final InputView inputView;
    private final OutputView outputView;
    private final ExceptionHandler exceptionHandler;
    private final RacingService racingService;

    public RacingController(final InputView inputView, final OutputView outputView,
                            final ExceptionHandler exceptionHandler,
                            final RacingService racingService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.exceptionHandler = exceptionHandler;
        this.racingService = racingService;
    }

    public void process() {
        //자동차 이름 입력
        CarNames carNames = makeCarNames();
    }

    private CarNames makeCarNames() {
        outputView.showRequestName();
        return CarNames.of(inputView.readRequestName());
    }
}
