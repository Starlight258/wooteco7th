package racingcar.dto;

import java.util.List;

public record CarsPositionDto(List<CarPositionDto> dtos) {

    public record CarPositionDto(String name, int position) {
    }
}
