package com.power_consumption_analysis.globalstreamsprocessor.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Builder
public class GlobalWattageMin {
    private String dateTime;
    private double avgWatts;
}
