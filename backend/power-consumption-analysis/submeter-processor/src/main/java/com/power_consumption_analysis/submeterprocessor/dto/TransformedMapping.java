package com.power_consumption_analysis.submeterprocessor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TransformedMapping {

    @JsonProperty("Mapping")
    String Mapping;

    @JsonProperty("Multiplier")
    String Multiplier;


}
