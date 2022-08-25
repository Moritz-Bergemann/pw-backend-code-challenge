package com.demo.pwbackendchallenge.battery;

import com.demo.pwbackendchallenge.battery.dto.AddBatteryDto;
import com.demo.pwbackendchallenge.battery.dto.BatteryPostcodeReportDto;

import java.util.List;

public interface BatteryService {
    void addBatteries(List<AddBatteryDto> batteryDtos);

    BatteryPostcodeReportDto getBatteriesByPostcode(int minPostcode, int maxPostcode);
}