package com.power_consumption_analysis.globalstreamsprocessor.config;

import com.power_consumption_analysis.globalstreamsprocessor.dto.GlobalVoltage;
import com.power_consumption_analysis.globalstreamsprocessor.util.TimeExtractorUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class VoltageEventTimeExtractor implements TimestampExtractor {

    @Autowired
    TimeExtractorUtil timeExtractorUtil;

    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long prevTime) {
        GlobalVoltage globalVoltage = (GlobalVoltage) consumerRecord.value();
        long capturedTimeStamp = timeExtractorUtil.extractedTimeStamp(null, globalVoltage);
        return (capturedTimeStamp > 0) ? capturedTimeStamp : prevTime;
    }


    @Bean
    public TimestampExtractor voltageTimeExtractor() {
        return new VoltageEventTimeExtractor();
    }
}
