package com.power_consumption_analysis.submeterprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SubmeterProcessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubmeterProcessorApplication.class, args);
    }

}


/*
submeter1  - dishwasher - 0.7
submeter1  - microwave  - 0.3

submeter2   - washer/dryer  - 0.35
submeter2   - a refrigerator - 0.6
submeter2   - lights - 0.05

submeter3  - water-heater - 0.6
submeter3  - air-conditioner. - 0.4

 */