package com.demo.pwbackendchallenge.battery;

import com.demo.pwbackendchallenge.battery.dto.AddBatteryDto;
import com.demo.pwbackendchallenge.battery.dto.BatteryPostcodeReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultBatteryService implements BatteryService {
    @Autowired
    private BatteryRepository batteryRepository;

    public void addBatteries(List<AddBatteryDto> batteryDtos) {
        //Convert DTOs to batteries
        List<Battery> batteries = batteryDtos.stream()
                .map(Battery::fromAddEntryDto)
                .toList();
        //TODO error handling here?
        //TODO check for duplicates
        batteryRepository.saveAll(batteries);
    }

    public BatteryPostcodeReportDto getBatteriesByPostcode(int minPostcode, int maxPostcode) {
        List<Battery> batteries = batteryRepository.findByPostCodeBetweenOrderByNameAsc(minPostcode, maxPostcode);

        List<String> batteryNames = batteries.stream()
                .map(Battery::getName)
                .toList();

        double totalWattageCapacity = batteries.stream()
                .mapToDouble(Battery::getWattageCapacity)
                .sum();

        //For efficiency, don't compute average with streams but divide total by count
        double averageWattageCapacity = totalWattageCapacity / (double)batteries.size();

        return new BatteryPostcodeReportDto(
            averageWattageCapacity,
                totalWattageCapacity,
                batteryNames
        );
    }
}
