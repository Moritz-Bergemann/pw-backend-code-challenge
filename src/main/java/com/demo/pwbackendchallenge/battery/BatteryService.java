package com.demo.pwbackendchallenge.battery;

import com.demo.pwbackendchallenge.battery.dto.AddBatteryConfirmDto;
import com.demo.pwbackendchallenge.battery.dto.AddBatteryDto;
import com.demo.pwbackendchallenge.battery.dto.BatteryPostcodeReportDto;

import java.util.List;

public interface BatteryService {
    List<AddBatteryConfirmDto> addBatteries(List<AddBatteryDto> batteryDtos) throws BadAddRequestException;

    BatteryPostcodeReportDto getBatteriesByPostcode(int minPostcode, int maxPostcode) throws BadSearchRequestException;
}