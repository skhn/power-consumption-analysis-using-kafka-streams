package com.power_consumption_analysis.submeterprocessor.config;

import com.power_consumption_analysis.submeterprocessor.dto.TransformedMapping;
import io.confluent.kafka.streams.serdes.json.KafkaJsonSchemaSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AppSerdes extends Serdes {


    /**
     * Issues with Deserialization -- more specifically - java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to
     * <p>
     * Need further investigation before use
     */
    private static final String schema_registry_url = "http://localhost:8081";

    public static Serde<TransformedMapping> transformedMappingSerde() {
        final Serde<TransformedMapping> specificJsonSerde = new KafkaJsonSchemaSerde<>();
        Map<String, String> serdeConfig = new HashMap<>();
        serdeConfig.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, specificJsonSerde.getClass().getName());
        serdeConfig.put("schema.registry.url", schema_registry_url);
        specificJsonSerde.configure(serdeConfig, false);
        return specificJsonSerde;
    }
}
