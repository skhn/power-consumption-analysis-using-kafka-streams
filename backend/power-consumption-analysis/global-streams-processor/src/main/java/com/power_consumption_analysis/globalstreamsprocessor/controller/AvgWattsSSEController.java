package com.power_consumption_analysis.globalstreamsprocessor.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.power_consumption_analysis.globalstreamsprocessor.service.EventProcessorService;
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
public class AvgWattsSSEController {

    @Autowired
    EventProcessorService eventProcessorService;


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(path = "/energy-data-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamEvents() {
        ObjectMapper objectMapper = new ObjectMapper();
        return Flux.interval(Duration.ofMinutes(1))
                .map(events -> {
                    try {
                        return objectMapper.writeValueAsString(eventProcessorService.getProcessedEvent()) + "\n";
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @GetMapping(value = "/health")
    public String health() {
        return "app is up";
    }
}
