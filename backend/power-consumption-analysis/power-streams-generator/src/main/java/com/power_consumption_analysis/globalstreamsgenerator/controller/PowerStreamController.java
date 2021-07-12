package com.power_consumption_analysis.globalstreamsgenerator.controller;

import com.power_consumption_analysis.globalstreamsgenerator.service.PowerStreamService;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Log4j2
public class PowerStreamController {

    private final PowerStreamService powerStreamService;

    public PowerStreamController(PowerStreamService powerStreamService) {
        this.powerStreamService = powerStreamService;
    }

    @GetMapping("/sendGStreamData")
    public void sendGStreamData() {

        try {
            powerStreamService.sendRecordToTopic();
        } catch (DataRetrievalFailureException e) {
            log.error(e);
        } catch (IOException e) {
            log.error("Problem closing input data file", e);
        }
    }
}
