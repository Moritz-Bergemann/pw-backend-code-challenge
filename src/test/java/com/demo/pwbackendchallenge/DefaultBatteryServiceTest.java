package com.demo.pwbackendchallenge;

import com.demo.pwbackendchallenge.battery.*;
import com.demo.pwbackendchallenge.battery.dto.AddBatteryConfirmDto;
import com.demo.pwbackendchallenge.battery.dto.AddBatteryDto;
import com.demo.pwbackendchallenge.battery.dto.BatteryPostcodeReportDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@WebMvcTest(DefaultBatteryService.class)
public class DefaultBatteryServiceTest {
    @MockBean
    BatteryRepository batteryRepository;

    @Autowired
    DefaultBatteryService batteryService;

    //Mock data
    List<Battery> MOCK_BATTERIES = Arrays.asList(
            new Battery(1L, "battery1", 101, 10.0),
            new Battery(2L, "battery2", 102, 20.0),
            new Battery(3L, "battery3", 103, 30.0)
    );

    @Test
    public void addBatteries_success() throws Exception {
        List<AddBatteryDto> batteryDtos = Arrays.asList(
                new AddBatteryDto("battery1", 101, 10.0),
                new AddBatteryDto("battery2", 102, 20.0),
                new AddBatteryDto("battery3", 103, 30.0)
        );

        List<Battery> batteries = Arrays.asList(
                new Battery(1L, "battery1", 101, 10.0),
                new Battery(1L, "battery2", 102, 20.0),
                new Battery(1L, "battery3", 103, 30.0)
        );

        // Manually set ID since database is not present
        doAnswer(invocationOnMock -> {
            List<Battery> batteriesToSave = invocationOnMock.getArgument(0);

            for (int ii = 0; ii < batteriesToSave.size(); ii++) {
                ReflectionTestUtils.setField(batteriesToSave.get(ii), "id", (long) ii);
            }
            return null;
        }).when(batteryRepository).saveAll(any());

        List<AddBatteryConfirmDto> result = batteryService.addBatteries(batteryDtos);

        Mockito.verify(batteryRepository, Mockito.times(1)).saveAll(Mockito.any());

        assertEquals(3, result.size());
        assertEquals(101, result.get(0).getPostcode());
        assertEquals("battery1", result.get(0).getName());
        assertEquals(103, result.get(2).getPostcode());
        assertEquals("battery3", result.get(2).getName());
    }

    @Test
    public void addBatteries_emptyList() throws Exception {
        List<AddBatteryDto> batteryDtos = new ArrayList<>();

        Exception exception = assertThrows(BadAddRequestException.class, () -> batteryService.addBatteries(batteryDtos));

        assertEquals("One or more battery details must be provided", exception.getMessage());
    }

    // getBatteriesByPostcode

    @Test
    public void getBatteriesByPostcode_success() throws Exception {
        List<Battery> batteries = Arrays.asList(
                new Battery(1L, "battery1", 101, 10.0),
                new Battery(1L, "battery2", 102, 20.0),
                new Battery(1L, "battery3", 103, 30.0)
        );

        int startPostcode = 101;
        int endPostcode = 103;

        when(batteryRepository.findByPostcodeBetweenOrderByNameAsc(101, 103)).thenReturn(batteries);

        BatteryPostcodeReportDto result = batteryService.getBatteriesByPostcode(startPostcode, endPostcode);

        Mockito.verify(batteryRepository, Mockito.times(1)).findByPostcodeBetweenOrderByNameAsc(startPostcode, endPostcode);
        assertEquals(20.0, result.getAverageWattageCapacity(),0.0001);
        assertEquals(60.0, result.getTotalWattageCapacity(), 0.0001);
        assertEquals(result.getBatteryNames().get(0), "battery1");
        assertEquals(result.getBatteryNames().get(1), "battery2");
        assertEquals(result.getBatteryNames().get(2), "battery3");
    }

    public void getBatteriesByPostcode_negativeStartPostcode() throws Exception {
        int startPostcode = -1;
        int endPostcode = 103;

        Exception exception = assertThrows(BadSearchRequestException.class,
                () -> batteryService.getBatteriesByPostcode(startPostcode, endPostcode));

        assertEquals("Postcode values cannot be negative", exception.getMessage());
    }

    public void getBatteriesByPostcode_negativeEndPostcode() throws Exception {
        int startPostcode = 101;
        int endPostcode = -1;

        Exception exception = assertThrows(BadSearchRequestException.class,
                () -> batteryService.getBatteriesByPostcode(startPostcode, endPostcode));

        assertEquals("Postcode values cannot be negative", exception.getMessage());
    }

    public void getBatteriesByPostcode_startPostcodeGreaterThanEndPostcode() throws Exception {
        int startPostcode = 103;
        int endPostcode = 101;

        Exception exception = assertThrows(BadSearchRequestException.class,
                () -> batteryService.getBatteriesByPostcode(startPostcode, endPostcode));

        assertEquals("Start postcode must be less than end postcode", exception.getMessage());
    }
}
