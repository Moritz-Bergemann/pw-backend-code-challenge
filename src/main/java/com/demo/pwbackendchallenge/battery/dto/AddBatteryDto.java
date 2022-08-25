package com.demo.pwbackendchallenge.battery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Data transfer object for battery details.
 */
public class AddBatteryDto {
    @JsonProperty(required = true)
    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @JsonProperty(required = true)
    @Min(value = 0, message = "Postcode cannot be negative")
    private int postcode;

    @JsonProperty(required = true)
    @DecimalMin(value = "0.0", message = "Battery capacity cannot be negative")
    private double wattageCapacity; //TODO should wattCapacity be double?

    public AddBatteryDto() {
    }

    public AddBatteryDto(String name, int postcode, double wattageCapacity) {
        this.name = name;
        this.postcode = postcode;
        this.wattageCapacity = wattageCapacity;
    }

    public String getName() {
        return name;
    }

    public int getPostcode() {
        return postcode;
    }

    public double getWattageCapacity() {
        return wattageCapacity;
    }
}
