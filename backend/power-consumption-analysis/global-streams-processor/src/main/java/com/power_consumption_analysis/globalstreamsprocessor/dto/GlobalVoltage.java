package com.power_consumption_analysis.globalstreamsprocessor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GlobalVoltage {

    @JsonProperty("Date")
    private String date;

    @JsonProperty("Time")
    private String time;

    @JsonProperty("Voltage")
    private String Voltage;
}
