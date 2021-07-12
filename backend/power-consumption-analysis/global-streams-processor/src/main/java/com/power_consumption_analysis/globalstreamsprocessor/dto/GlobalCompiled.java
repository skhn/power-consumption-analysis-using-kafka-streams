package com.power_consumption_analysis.globalstreamsprocessor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GlobalCompiled {


    @JsonProperty("Date")
    private String date;

    @JsonProperty("Time")
    private String time;

    @JsonProperty("Global_intensity")
    private String current;

    @JsonProperty("Voltage")
    private String voltage;

}
