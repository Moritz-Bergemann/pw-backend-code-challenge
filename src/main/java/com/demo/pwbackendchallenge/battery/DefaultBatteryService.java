package com.demo.pwbackendchallenge.battery;

import com.demo.pwbackendchallenge.battery.dto.AddBatteryConfirmDto;
import com.demo.pwbackendchallenge.battery.dto.AddBatteryDto;
import com.demo.pwbackendchallenge.battery.dto.BatteryPostcodeReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultBatteryService implements BatteryService {
    @Autowired
    private BatteryRepository batteryRepository;

    public List<AddBatteryConfirmDto> addBatteries(List<AddBatteryDto> batteryDtos) {
        //Convert DTOs to batteries
        List<Battery> batteries = batteryDtos.stream()
                .map(Battery::fromAddEntryDto)
                .toList();

        batteryRepository.saveAll(batteries);

        return batteries.stream()
                .map(AddBatteryConfirmDto::fromBattery)
                .toList();
    }

    public BatteryPostcodeReportDto getBatteriesByPostcode(int minPostcode, int maxPostcode) throws BadSearchRequestException {
        // Sanity checks
        if (minPostcode > maxPostcode) {
            throw new BadSearchRequestException("Minimum postcode must be less than maximum postcode");
        }
        if (minPostcode < 0 || maxPostcode < 0) {
            throw new BadSearchRequestException("Postcode values cannot be negative");
        }

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
