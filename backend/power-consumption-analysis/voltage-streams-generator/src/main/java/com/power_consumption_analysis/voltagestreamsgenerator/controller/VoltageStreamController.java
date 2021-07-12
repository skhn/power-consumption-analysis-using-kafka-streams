package com.power_consumption_analysis.voltagestreamsgenerator.controller;

import com.power_consumption_analysis.voltagestreamsgenerator.service.VoltageStreamService;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Log4j2
public class VoltageStreamController {

    private final VoltageStreamService voltageStreamService;

    public VoltageStreamController(VoltageStreamService voltageStreamService) {
        this.voltageStreamService = voltageStreamService;
    }

    @GetMapping("/sendVStreamData")
    public void sendGStreamData() {

        try {
            voltageStreamService.sendRecordToTopic();
        } catch (DataRetrievalFailureException e) {
            log.error(e);
        } catch (IOException e) {
            log.error("Problem closing input data file", e);
        }
    }
}
