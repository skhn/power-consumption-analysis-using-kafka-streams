package com.power_consumption_analysis.voltagestreamsgenerator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GlobalVoltage {

    @JsonProperty("Date")
    String date;

    @JsonProperty("Time")
    String time;

    @JsonProperty("Voltage")
    String Voltage;
}
