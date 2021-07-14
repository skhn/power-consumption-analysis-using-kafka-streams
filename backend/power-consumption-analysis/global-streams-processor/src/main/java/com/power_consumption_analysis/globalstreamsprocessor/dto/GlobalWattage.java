package com.power_consumption_analysis.globalstreamsprocessor.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GlobalWattage {

    private String dateTime;
    private double totalWatts;
    private int instanceCount;
    private double avgWatts;

}
