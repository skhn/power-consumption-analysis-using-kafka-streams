package com.power_consumption_analysis.submeterprocessor.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.power_consumption_analysis.submeterprocessor.dto.EnrichedS2Reading;
import com.power_consumption_analysis.submeterprocessor.service.EventProcessorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@Log4j2
@RequestMapping(value = "/v1")
public class SubMeter2DevicesController {

    @Autowired
    EventProcessorService eventProcessorService;


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = "/sub-2-devices-data-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EnrichedS2Reading> streamEvents() {
        return Flux.interval(Duration.ofMillis(500))
                .map(events -> {
                    EnrichedS2Reading captured =  eventProcessorService.getProcessedEvent();
                    return null == captured ? EnrichedS2Reading.builder()
                            .date("--")
                            .time("--")
                    .mapping(null)
                    .build() : captured;
                });
    }

    @GetMapping(value = "/health")
    public String health() {
        return "app is up";
    }
}
