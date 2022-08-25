package com.demo.pwbackendchallenge.battery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class AddBatteryCollectionDto {
    @JsonProperty(required = true)
    @Valid
    @NotEmpty
    private List<AddBatteryDto> batteries;

    public AddBatteryCollectionDto() {
    }

    public AddBatteryCollectionDto(List<AddBatteryDto> batteries) {
        this.batteries = batteries;
    }

    public List<AddBatteryDto> getBatteries() {
        return batteries;
    }
}
