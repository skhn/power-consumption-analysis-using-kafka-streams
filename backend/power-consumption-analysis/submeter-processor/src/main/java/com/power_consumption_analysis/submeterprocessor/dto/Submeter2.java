package com.power_consumption_analysis.submeterprocessor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Submeter2 {


    @JsonProperty("Date")
    String date;
    @JsonProperty("Time")
    String time;
    @JsonProperty("Sub_metering_2")
    String sub2;

}
