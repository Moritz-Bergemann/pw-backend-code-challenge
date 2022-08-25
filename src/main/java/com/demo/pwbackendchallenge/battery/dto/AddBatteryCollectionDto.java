package com.demo.pwbackendchallenge.battery.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class AddBatteryCollectionDto {
    @Valid
    @NotEmpty
    private List<AddBatteryDto> batteries;

    public List<AddBatteryDto> getBatteries() {
        return batteries;
    }
}
