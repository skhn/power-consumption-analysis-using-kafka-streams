package com.power_consumption_analysis.submeterprocessor.listener;


import com.kinandcarta.usfoods.kafka.submeter2.Mappings;
import com.kinandcarta.usfoods.kafka.submeter2.Submeter2;
import com.power_consumption_analysis.submeterprocessor.binding.S2EventBinding;
import com.power_consumption_analysis.submeterprocessor.dto.EnrichedS2Reading;
import com.power_consumption_analysis.submeterprocessor.dto.TransformedMapping;
import com.power_consumption_analysis.submeterprocessor.service.EventProcessorService;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Log4j2
@EnableBinding(S2EventBinding.class)
public class S2ListenerService {

    private final Map<String, TransformedMapping> configMap = new HashMap<>();
    @Value("spring.cloud.stream.bindings.s2-output-channel.destination")
    String TOPIC;
    @Value("application.configs.error.topic.name")
    String ERROR_TOPIC;


    @Autowired
    EventProcessorService eventProcessorService;

    @StreamListener
    public void process(@Input("s2-input-channel") KStream<String, Submeter2> s2Stream,
                        @Input("mapping-input-channel") KTable<String, Mappings> mappings) {


        mappings.toStream()
                .filter((k, v) -> v.getStream().equals("Sub_metering_2"))
                .map(
                        (k, v) -> new KeyValue<>(v.getMapping(), TransformedMapping.builder()
                                .Mapping(v.getMapping())
                                .Multiplier(v.getMultiplier()).build())

                ).foreach(configMap::put);

        s2Stream.map((k, v) -> {

            List<Map<String, String>> mapping = new ArrayList<>();
            Map<String, String> map = new HashMap<>();
            for (var entry : configMap.entrySet()) {
                map.put(entry.getKey(), String.valueOf(Double.parseDouble(entry.getValue().getMultiplier()) * Double.parseDouble(v.getSubMetering2())));
            }
            mapping.add(map);
            return new KeyValue<String, EnrichedS2Reading>(
                    k, EnrichedS2Reading.builder()
                    .date(v.getDate())
                    .time(v.getTime())
                    .s2Meter(Double.parseDouble(v.getSubMetering2()))
                    .mapping(mapping)
                    .build()


            );

        }).peek((k, v) -> {

            String originalFormat = "dd/MM/yyyy:HH:mm:ss";
            String correctedFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";

            SimpleDateFormat inputFormat = new SimpleDateFormat(originalFormat);
            SimpleDateFormat outputFormat = new SimpleDateFormat(correctedFormat);

            try {
                Date date = inputFormat.parse(k);
                v.setDate(outputFormat.format(date));
            } catch (ParseException e) {
                log.info("Error parsing Date");
            }

            eventProcessorService.handleEvent(v);
            log.info("Key: {} and Value: {}", k, v);
        });
    }

}
