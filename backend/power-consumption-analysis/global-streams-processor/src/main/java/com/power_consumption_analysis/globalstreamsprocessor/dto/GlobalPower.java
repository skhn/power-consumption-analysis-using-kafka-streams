package com.power_consumption_analysis.globalstreamsprocessor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GlobalPower {

    @JsonProperty("Date")
    private String date;

    @JsonProperty("Time")
    private String time;

    @JsonProperty("Global_active_power")
    private String globalActivePower;

    @JsonProperty("Global_reactive_power")
    private String globalReactivePower;

    @JsonProperty("Global_intensity")
    private String globalIntensity;
}
