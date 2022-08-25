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
    private int postcode;

    public Battery() {
    }

    public Battery(String name, double wattageCapacity, int postcode) {
        this.name = name;
        this.wattageCapacity = wattageCapacity;
        this.postcode = postcode;
    }

    public Battery(Long id, String name, int postcode, double wattageCapacity) {
        this.id = id;
        this.name = name;
        this.wattageCapacity = wattageCapacity;
        this.postcode = postcode;
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

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
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
