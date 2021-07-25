package com.power_consumption_analysis.submeter2streamsgenerator.controller;


import com.power_consumption_analysis.submeter2streamsgenerator.service.MappingTableStreamService;
import com.power_consumption_analysis.submeter2streamsgenerator.service.Submeter2StreamService;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Log4j2
@RequestMapping("v1")
public class Submeter2StreamController {

    private final Submeter2StreamService submeter2StreamService;
    private final MappingTableStreamService mappingTableStreamService;

    public Submeter2StreamController(Submeter2StreamService submeter2StreamService, MappingTableStreamService mappingTableStreamService) {
        this.submeter2StreamService = submeter2StreamService;
        this.mappingTableStreamService = mappingTableStreamService;
    }

    @GetMapping("/sendS2StreamData")
    public void sendGStreamData() {

        try {
            submeter2StreamService.sendRecordToTopic();
        } catch (DataRetrievalFailureException e) {
            log.error(e);
        } catch (IOException e) {
            log.error("Problem closing input data file", e);
        }
    }

    @GetMapping("/sendMapInformation")
    public void sendMapData() {
        mappingTableStreamService.sendRecordToTopic();
    }
}
