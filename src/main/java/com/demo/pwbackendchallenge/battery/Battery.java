package com.demo.pwbackendchallenge.battery;

import com.demo.pwbackendchallenge.battery.dto.AddBatteryDto;

import javax.persistence.*;

@Entity
public class Battery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double wattageCapacity;
    private int postCode;

    public Battery() {
    }

    public Battery(String name, double wattageCapacity, int postCode) {
        this.name = name;
        this.wattageCapacity = wattageCapacity;
        this.postCode = postCode;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWattageCapacity() {
        return wattageCapacity;
    }

    public void setWattageCapacity(double wattageCapacity) {
        this.wattageCapacity = wattageCapacity;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    /**
     * Helper method for constructing valid BatteryEntry from AddEntryDto
     * @return a new valid BatteryEntry
     */
    public static Battery fromAddEntryDto(AddBatteryDto addBatteryDto) {
        return new Battery(
                addBatteryDto.getName(),
                addBatteryDto.getWattageCapacity(),
                addBatteryDto.getPostcode()
        );
    }
}
