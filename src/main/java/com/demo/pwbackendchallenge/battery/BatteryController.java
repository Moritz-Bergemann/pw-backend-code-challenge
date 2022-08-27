package com.demo.pwbackendchallenge.battery;

import com.demo.pwbackendchallenge.battery.dto.AddBatteryCollectionDto;
import com.demo.pwbackendchallenge.battery.dto.AddBatteryConfirmCollectionDto;
import com.demo.pwbackendchallenge.battery.dto.AddBatteryConfirmDto;
import com.demo.pwbackendchallenge.battery.dto.BatteryPostcodeReportDto;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/battery")
public class BatteryController {
    private static final String RESPONSE_OK_MESSAGE_TEMPLATE = "%s batteries successfully added.";
    @Autowired
    private BatteryService service;
    Logger logger = LoggerFactory.getLogger(BatteryController.class);
    private BatteryPostcodeReportDto reportDto;

    /**
     * API endpoint for adding batteries to the database.
     */
    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<AddBatteryConfirmCollectionDto> addBatteries(@Valid @RequestBody AddBatteryCollectionDto addBatteriesDto) {
        List<AddBatteryConfirmDto> addedBatteries;
        try {
            addedBatteries = service.addBatteries(addBatteriesDto.getBatteries());
        } catch (BadAddRequestException ex) {
            logger.info(String.format("Error adding requested batteries - %s", ex.getMessage()));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Bad add request: %s", ex.getMessage()));
        }

        AddBatteryConfirmCollectionDto confirmDto = new AddBatteryConfirmCollectionDto(addedBatteries);

        long[] batteryIds = confirmDto.getBatteries().stream().mapToLong(AddBatteryConfirmDto::getId).toArray();
        logger.info(String.format("Successfully added '%s' new batteries - ids = %s", addedBatteries.size(), Arrays.toString(batteryIds)));

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
            logger.info(String.format("Error retrieving batteries in range '%d'-'%d' - %s", startPostcode, endPostcode, ex.getMessage()));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Bad search request: %s", ex.getMessage()));
        }

        logger.info(String.format("Successfully retrieved %d batteries in postcode range '%d'-'%d'", reportDto.getBatteryNames().size(), startPostcode, endPostcode));

        return new ResponseEntity<>(reportDto, HttpStatus.OK);
    }
}
