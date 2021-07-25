package com.power_consumption_analysis.submeter2streamsgenerator.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "application.config.data")
@Getter
@Setter
@Component
public class ConfigEquipmentUsageMap {
    Map<String, Double> mapping;
}
