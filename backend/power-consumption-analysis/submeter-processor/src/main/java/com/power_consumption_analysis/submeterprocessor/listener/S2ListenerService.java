package com.power_consumption_analysis.submeterprocessor.listener;


import com.kinandcarta.usfoods.kafka.submeter2.Mappings;
import com.kinandcarta.usfoods.kafka.submeter2.Submeter2;
import com.power_consumption_analysis.submeterprocessor.binding.S2EventBinding;
import com.power_consumption_analysis.submeterprocessor.dto.EnrichedS2Reading;
import com.power_consumption_analysis.submeterprocessor.dto.TransformedMapping;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@EnableBinding(S2EventBinding.class)
public class S2ListenerService {

    private final Map<String, TransformedMapping> configMap = new HashMap<>();
    @Value("spring.cloud.stream.bindings.s2-output-channel.destination")
    String TOPIC;
    @Value("application.configs.error.topic.name")
    String ERROR_TOPIC;

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
                    .s2Meter(v.getSubMetering2())
                    .mapping(mapping)
                    .build()


            );

        }).peek((k, v) -> log.info(v));
    }

}
