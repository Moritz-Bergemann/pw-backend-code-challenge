package com.demo.pwbackendchallenge.battery.dto;

import com.demo.pwbackendchallenge.battery.Battery;

public class AddBatteryConfirmDto {
    private final long id;
    private final String name;
    private final int postcode;
    private final double wattageCapacity;

    public AddBatteryConfirmDto(long id, String name, int postcode, double wattageCapacity) {
        this.id = id;
        this.name = name;
        this.postcode = postcode;
        this.wattageCapacity = wattageCapacity;
    }

    public long getId() {
        return id;
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

    /**
     * Helper method for converting Battery to AddBatteryConfirmationDto
     */
    public static AddBatteryConfirmDto fromBattery(Battery battery) {
        return new AddBatteryConfirmDto(
                battery.getId(),
                battery.getName(),
                battery.getPostcode(),
                battery.getWattageCapacity()
        );
    }
}
