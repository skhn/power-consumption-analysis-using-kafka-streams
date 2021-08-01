package com.power_consumption_analysis.submeterprocessor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class EnrichedS2Reading {


    @JsonProperty("Date")
    private String date;

    @JsonProperty("Time")
    private String time;

    @JsonProperty("Sub_metering_2")
    private Double s2Meter;

    @JsonProperty("Mapping")
    private List<Map<String, String>> mapping;
}
