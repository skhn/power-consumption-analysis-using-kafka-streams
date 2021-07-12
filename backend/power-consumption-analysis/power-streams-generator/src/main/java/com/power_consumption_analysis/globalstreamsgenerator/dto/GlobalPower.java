package com.power_consumption_analysis.globalstreamsgenerator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GlobalPower {

    @JsonProperty("Date")
    String date;

    @JsonProperty("Time")
    String time;

    @JsonProperty("Global_active_power")
    String globalActivePower;

    @JsonProperty("Global_reactive_power")
    String globalReactivePower;

    @JsonProperty("Global_intensity")
    String globalIntensity;
}
