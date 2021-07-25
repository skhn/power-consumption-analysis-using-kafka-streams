package com.power_consumption_analysis.submeterprocessor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Mappings {

    @JsonProperty("Stream")
    String meter;
    @JsonProperty("Mapping")
    String map;
    @JsonProperty("Multiplier")
    String mult;
}
