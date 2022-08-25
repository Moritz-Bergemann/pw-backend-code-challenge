package com.demo.pwbackendchallenge.battery;

import com.demo.pwbackendchallenge.battery.dto.AddBatteryCollectionDto;
import com.demo.pwbackendchallenge.battery.dto.AddBatteryConfirmCollectionDto;
import com.demo.pwbackendchallenge.battery.dto.AddBatteryConfirmDto;
import com.demo.pwbackendchallenge.battery.dto.BatteryPostcodeReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/battery")
public class BatteryController {
    private static final String RESPONSE_OK_MESSAGE_TEMPLATE = "%s batteries successfully added.";
    @Autowired
    private BatteryService service;

    /**
     * API endpoint for adding batteries to the database.
     */
    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<AddBatteryConfirmCollectionDto> addBatteries(@Valid @RequestBody AddBatteryCollectionDto addBatteriesDto) {
        List<AddBatteryConfirmDto> addedBatteries;
        try {
            addedBatteries = service.addBatteries(addBatteriesDto.getBatteries());
        } catch (BadAddRequestException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Bad add request: %s", ex.getMessage()));
        }

        AddBatteryConfirmCollectionDto confirmDto = new AddBatteryConfirmCollectionDto(addedBatteries);

        return new ResponseEntity<>(confirmDto, HttpStatus.OK);
    }

    /**
     * API endpoint for retrieving batteries by postcode from the database.
     */
    @GetMapping
    public ResponseEntity<BatteryPostcodeReportDto> getBatteriesByPostcode(@RequestParam int startPostcode, @RequestParam int endPostcode) { //FIXME requestbody may be wrong
        BatteryPostcodeReportDto reportDto;
        try {
            reportDto = service.getBatteriesByPostcode(startPostcode, endPostcode);
        } catch (BadSearchRequestException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Bad search request: %s", ex.getMessage()));
        }

        return new ResponseEntity<>(reportDto, HttpStatus.OK);
    }
}
