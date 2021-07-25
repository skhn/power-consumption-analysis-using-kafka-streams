package com.power_consumption_analysis.submeterprocessor.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MappingsManyToOne {

 String stream;

 List<TransformedMapping> transformedMappings;


 @Override
 public String toString() {
  return "MappingsManyToOne{" +
          "stream='" + stream + '\'' +
          ", transformedMappings=" + transformedMappings +
          '}';
 }
}
