package com.demo.pwbackendchallenge.battery;

import com.demo.pwbackendchallenge.battery.dto.AddBatteryConfirmDto;
import com.demo.pwbackendchallenge.battery.dto.AddBatteryDto;
import com.demo.pwbackendchallenge.battery.dto.BatteryPostcodeReportDto;

import java.util.List;

public interface BatteryService {
    /**
     * Add a set of batteries (defined by DTOs) to the database
     * @throws BadAddRequestException if no batteries provided
     */
    List<AddBatteryConfirmDto> addBatteries(List<AddBatteryDto> batteryDtos) throws BadAddRequestException;

    /**
     * Retrieve all databases between minPostcode and maxPostcode
     * @throws BadSearchRequestException if minPostcode or maxPostcode are invalid
     */
    BatteryPostcodeReportDto getBatteriesByPostcode(int minPostcode, int maxPostcode) throws BadSearchRequestException;
}