package com.demo.pwbackendchallenge.battery;

import com.demo.pwbackendchallenge.battery.dto.AddBatteryCollectionDto;
import com.demo.pwbackendchallenge.battery.dto.BatteryPostcodeReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/batteries")
public class BatteryController {
    private static final String RESPONSE_OK_MESSAGE_TEMPLATE = "%s batteries successfully added.";

    @Autowired
    private BatteryService service;

    @PostMapping
    public ResponseEntity<String> addBatteries(@Valid AddBatteryCollectionDto addBatteriesDto) {
        service.addBatteries(addBatteriesDto.getBatteries());

        int batteriesAdded = addBatteriesDto.getBatteries().size();
        // TODO return the whole object (inc ID) in a new object
        return new ResponseEntity<>(String.format(RESPONSE_OK_MESSAGE_TEMPLATE, batteriesAdded), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<BatteryPostcodeReportDto> getBatteriesByPostcode(@RequestBody int startPostCode, int endPostCode) { //FIXME requestbody may be wrong
        BatteryPostcodeReportDto reportDto = service.getBatteriesByPostcode(startPostCode, endPostCode); //TODO error handling for bad postcode values (i.e. start > end)\

        return new ResponseEntity<>(reportDto, HttpStatus.OK);
    }

    @GetMapping("api/test")
    public ResponseEntity<String> basicTest() {
        return new ResponseEntity<>("The API works at all!", HttpStatus.OK);
    }
}
