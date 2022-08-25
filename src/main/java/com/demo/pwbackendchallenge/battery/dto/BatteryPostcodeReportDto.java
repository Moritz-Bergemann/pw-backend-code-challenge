package com.demo.pwbackendchallenge.battery.dto;

import java.util.List;

public class BatteryPostcodeReportDto {
    private double averageWattageCapacity;
    private double totalWattageCapacity;
    private List<String> batteryNames;

    public BatteryPostcodeReportDto() {
    }

    public BatteryPostcodeReportDto(double averageWattageCapacity, double totalWattageCapacity, List<String> batteryNames) {
        this.averageWattageCapacity = averageWattageCapacity;
        this.totalWattageCapacity = totalWattageCapacity;
        this.batteryNames = batteryNames;
    }

    public double getAverageWattageCapacity() {
        return averageWattageCapacity;
    }

    public double getTotalWattageCapacity() {
        return totalWattageCapacity;
    }

    public List<String> getBatteryNames() {
        return batteryNames;
    }
}
