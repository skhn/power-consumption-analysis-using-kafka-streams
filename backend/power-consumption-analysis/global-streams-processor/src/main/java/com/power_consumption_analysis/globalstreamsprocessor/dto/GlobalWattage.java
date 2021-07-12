package com.power_consumption_analysis.globalstreamsprocessor.dto;


import lombok.Data;

@Data
public class GlobalWattage {

    private double totalWatts;
    private int instanceCount;
    private double avgWatts;

}
