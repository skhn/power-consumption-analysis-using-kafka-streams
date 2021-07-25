package com.power_consumption_analysis.submeter2streamsgenerator.service;

import com.kinandcarta.usfoods.kafka.submeter2.Mappings;
import com.power_consumption_analysis.submeter2streamsgenerator.dto.ConfigEquipmentUsageMap;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class MappingTableStreamService {

    @Autowired
    ConfigEquipmentUsageMap configEquipmentUsageMap;
    @Autowired
    private KafkaTemplate<String, Mappings> template;
    @Value("${application.config.kafka.topic-name-map}")
    private String topic;

    public void sendRecordToTopic() {

        configEquipmentUsageMap.getMapping().entrySet().stream().map((entry) -> Mappings.newBuilder()
                .setStream("Sub_metering_2")
                .setMapping(entry.getKey())
                .setMultiplier(String.valueOf(entry.getValue() / 100))
                .build()).forEach(
                obj -> {
                    log.info("sending to ktable topic: {}", obj);
                    template.send(topic, obj.getMapping(), obj);
                }
        );
    }
}
