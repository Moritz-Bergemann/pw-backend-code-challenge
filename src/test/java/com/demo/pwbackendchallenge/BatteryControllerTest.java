package com.demo.pwbackendchallenge;

import com.demo.pwbackendchallenge.battery.BadAddRequestException;
import com.demo.pwbackendchallenge.battery.BadSearchRequestException;
import com.demo.pwbackendchallenge.battery.BatteryController;
import com.demo.pwbackendchallenge.battery.BatteryService;
import com.demo.pwbackendchallenge.battery.dto.AddBatteryCollectionDto;
import com.demo.pwbackendchallenge.battery.dto.AddBatteryConfirmDto;
import com.demo.pwbackendchallenge.battery.dto.AddBatteryDto;
import com.demo.pwbackendchallenge.battery.dto.BatteryPostcodeReportDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BatteryController.class)
public class BatteryControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    BatteryService batteryService;

    //addBatteries
    @Test
    public void addBatteries_success() throws Exception {
        AddBatteryCollectionDto addBatteryCollectionDto = new AddBatteryCollectionDto(
                Arrays.asList(
                        new AddBatteryDto("battery1", 101, 10.0),
                        new AddBatteryDto("battery2", 102, 20.0),
                        new AddBatteryDto("battery3", 103, 30.0)
                )
        );

        List<AddBatteryConfirmDto> addedBatteries = Arrays.asList(
                new AddBatteryConfirmDto(1L, "battery1", 101, 10.0),
                new AddBatteryConfirmDto(2L, "battery2", 102, 20.0),
                new AddBatteryConfirmDto(3L, "battery3", 103, 30.0)
        );

        when(this.batteryService.addBatteries(Mockito.any())).thenReturn(addedBatteries);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/battery")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(addBatteryCollectionDto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.batteries[0].id", is(1)))
                .andExpect(jsonPath("$.batteries[0].name", is("battery1")))
                .andExpect(jsonPath("$.batteries[2].id", is(3)))
                .andExpect(jsonPath("$.batteries[2].name", is("battery3")));
    }

    @Test
    public void addBatteries_emptyList() throws Exception {
        AddBatteryCollectionDto addBatteryCollectionDto = new AddBatteryCollectionDto(new ArrayList<>());

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/battery")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(addBatteryCollectionDto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addBatteries_negativePostcode() throws Exception {
        AddBatteryCollectionDto addBatteryCollectionDto = new AddBatteryCollectionDto(
                Arrays.asList(
                        new AddBatteryDto("battery1", -101, 10.0)
                )
        );

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/battery")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(addBatteryCollectionDto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addBatteries_emptyName() throws Exception {
        AddBatteryCollectionDto addBatteryCollectionDto = new AddBatteryCollectionDto(
                Arrays.asList(
                        new AddBatteryDto("", 101, 10.0)
                )
        );

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/battery")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(addBatteryCollectionDto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addBatteries_negativeWattage() throws Exception {
        AddBatteryCollectionDto addBatteryCollectionDto = new AddBatteryCollectionDto(
                Arrays.asList(
                        new AddBatteryDto("", 101, -10.0)
                )
        );

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/battery")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(addBatteryCollectionDto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
    }

    //getBatteries

    @Test
    public void getBatteries_success() throws Exception {
        BatteryPostcodeReportDto reportDto = new BatteryPostcodeReportDto(10.0, 30.0, Arrays.asList(
                "battery1",
                "battery2",
                "battery3"
        ));

        when(batteryService.getBatteriesByPostcode(101, 103)).thenReturn(reportDto);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/battery")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("startPostcode", "101")
                .queryParam("endPostcode", "103");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageWattageCapacity", is(10.0)))
                .andExpect(jsonPath("$.totalWattageCapacity", is(30.0)))
                .andExpect(jsonPath("$.batteryNames[0]", is("battery1")))
                .andExpect(jsonPath("$.batteryNames[2]", is("battery3")));
    }

    @Test
    public void getBatteries_negativeStartPostcode() throws Exception {
        when(batteryService.getBatteriesByPostcode(-1, 103)).thenThrow(new BadSearchRequestException("Postcode values cannot be negative"));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/battery")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("startPostcode", "-1")
                .queryParam("endPostcode", "103");

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());    }

    @Test
    public void getBatteries_negativeEndPostcode() throws Exception {
        when(batteryService.getBatteriesByPostcode(101, -1)).thenThrow(new BadSearchRequestException("Postcode values cannot be negative"));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/battery")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("startPostcode", "101")
                .queryParam("endPostcode", "-1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());    }

    @Test
    public void getBatteries_startPostcodeGreaterThanEndPostcode() throws Exception {
        when(batteryService.getBatteriesByPostcode(103, 101)).thenThrow(new BadSearchRequestException("Start postcode must be less than end postcode"));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/battery")
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("startPostcode", "103")
                .queryParam("endPostcode", "101");

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getBatteries_missingParameters() throws Exception {
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/api/battery")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
    }

}
