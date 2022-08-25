package com.demo.pwbackendchallenge.battery.dto;

import java.util.List;

public class AddBatteryConfirmCollectionDto {
    private final List<AddBatteryConfirmDto> batteries;

    public AddBatteryConfirmCollectionDto(List<AddBatteryConfirmDto> batteries) {
        this.batteries = batteries;
    }

    public List<AddBatteryConfirmDto> getBatteries() {
        return batteries;
    }
}
