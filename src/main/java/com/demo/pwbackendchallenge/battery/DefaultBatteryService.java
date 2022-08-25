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

    /**
     * Adds the provided set of battery DTO representations to the database
     */
    public List<AddBatteryConfirmDto> addBatteries(List<AddBatteryDto> batteryDtos) throws BadAddRequestException {
        // Sanity checks
        if (batteryDtos.size() == 0) {
            throw new BadAddRequestException("One or more battery details must be provided");
        }

        // Convert DTOs to batteries
        List<Battery> batteries = batteryDtos.stream()
                .map(Battery::fromAddEntryDto)
                .toList();

        batteryRepository.saveAll(batteries);

        return batteries.stream()
                .map(AddBatteryConfirmDto::fromBattery)
                .toList();
    }

    /**
     * Retrieves a set of batteries in postcode DTO format from the database.
     */
    public BatteryPostcodeReportDto getBatteriesByPostcode(int startPostcode, int endPostcode) throws BadSearchRequestException {
        // Sanity checks
        if (startPostcode < 0 || endPostcode < 0) {
            throw new BadSearchRequestException("Postcode values cannot be negative");
        }
        if (startPostcode > endPostcode) {
            throw new BadSearchRequestException("Start postcode must be less than end postcode");
        }

        List<Battery> batteries = batteryRepository.findByPostcodeBetweenOrderByNameAsc(startPostcode, endPostcode);

        List<String> batteryNames = batteries.stream()
                .map(Battery::getName)
                .sorted()
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
