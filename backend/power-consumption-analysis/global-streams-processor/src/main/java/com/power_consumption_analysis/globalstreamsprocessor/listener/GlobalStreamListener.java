package com.power_consumption_analysis.globalstreamsprocessor.listener;


import com.power_consumption_analysis.globalstreamsprocessor.binding.PCAGlobalBinding;
import com.power_consumption_analysis.globalstreamsprocessor.dto.GlobalCompiled;
import com.power_consumption_analysis.globalstreamsprocessor.dto.GlobalPower;
import com.power_consumption_analysis.globalstreamsprocessor.dto.GlobalVoltage;
import com.power_consumption_analysis.globalstreamsprocessor.dto.GlobalWattage;
import com.power_consumption_analysis.globalstreamsprocessor.service.EventProcessorService;
import com.power_consumption_analysis.globalstreamsprocessor.util.RecordBuilder;
import com.power_consumption_analysis.globalstreamsprocessor.util.TimeExtractorUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Log4j2
@Service
@EnableBinding(PCAGlobalBinding.class)
public class GlobalStreamListener {

    @Autowired
    TimeExtractorUtil timeExtractorUtil;

    @Autowired
    RecordBuilder recordBuilder;

    @Value("spring.cloud.stream.bindings.active-energy-channel.destination")
    String TOPIC;


    @Value("application.configs.error.topic.name")
    String ERROR_TOPIC;

    @Autowired
    EventProcessorService eventProcessorService;

    @StreamListener
    public void process(@Input("global-input-channel") KStream<String, GlobalPower> powerStream,
                        @Input("voltage-input-channel") KStream<String, GlobalVoltage> voltageStream) {


        KStream<String, GlobalCompiled> joinedStream = powerStream.join(voltageStream,
                (r, c) -> recordBuilder.getGlobalCompiled(r, c),
                JoinWindows.of(Duration.ofMinutes(5)),
                StreamJoined.with(Serdes.String(),
                        new JsonSerde<>(GlobalPower.class),
                        new JsonSerde<>(GlobalVoltage.class)));


        joinedStream.filter((k, v) -> !isParseable(v))
                .to(ERROR_TOPIC, Produced.with(Serdes.String(), new JsonSerde<>(GlobalCompiled.class)));

        joinedStream
                .filter((k, v) -> isParseable(v))
                .map((k, v) -> new KeyValue<>(k.split(":")[0] + " : HH :" + k.split(":")[1],
                        (Double.parseDouble(v.getCurrent()) * Double.parseDouble(v.getVoltage()))))
                .groupByKey(Serialized.with(Serdes.String(), Serdes.Double()))
                .aggregate(
                        () -> recordBuilder.init(),
                        (k, v, aggV) -> recordBuilder.aggregate(v, aggV),
                        Materialized.<String, GlobalWattage, KeyValueStore<Bytes, byte[]>>as("wattage")
                                .withKeySerde(Serdes.String())
                                .withValueSerde(new JsonSerde<>(GlobalWattage.class)))
                .toStream()
                .peek((k, v) -> {
                    v.setDateTime(k);
                    eventProcessorService.handleEvent(v);
                    log.info("Key: {} and Value: {}", k, v);
                })
                .to(TOPIC, Produced.with(Serdes.String(), new JsonSerde<>(GlobalWattage.class)));
    }

    private boolean isParseable(GlobalCompiled v) {
        try {
            Double.parseDouble(v.getVoltage());
            Double.parseDouble(v.getCurrent());
            return true;
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }
}


//handle ? values
// active energy consumed watt hour = (global_active_power)*1000/60 - s1 - s2 - s3
// there is a multiplier of approx 4x in aggValue need to investigate.
// currently values are distorted very slightly

/*
234.840
18.400
2021-07-11 20:18:23.313  INFO 21470 --- [-StreamThread-1] o.a.k.s.s.i.RocksDBTimestampedStore      : Opening store KSTREAM-REDUCE-STATE-STORE-0000000008.1166270400000 in regular mode
2021-07-11 20:18:23.323  INFO 21470 --- [-StreamThread-1] c.p.g.listener.GlobalStreamListener      : Key: [16/12/2006:17:24:00@ Saturday, December 16, 2006 5:00:00 PM/Saturday, December 16, 2006 6:00:00 PM and Value: 64815.839999999975
2021-07-11 20:18:23.324  INFO 21470 --- [-StreamThread-1] c.p.g.listener.GlobalStreamListener      : Key: [16/12/2006:17:25:00@1166310000000/1166313600000] and Value: 80602.34999999999
2021-07-11 20:18:23.324  INFO 21470 --- [-StreamThread-1] c.p.g.listener.GlobalStreamListener      : Key: [16/12/2006:17:26:00@1166310000000/1166313600000] and Value: 80485.04999999999
2021-07-11 20:18:23.324  INFO 21470 --- [-StreamThread-1] c.p.g.listener.GlobalStreamListener      : Key: [16/12/2006:17:27:00@1166310000000/1166313600000] and Value: 80640.30000000003
2021-07-11 20:18:23.324  INFO 21470 --- [-StreamThread-1] c.p.g.listener.GlobalStreamListener      : Key: [16/12/2006:17:28:00@1166310000000/1166313600000] and Value: 55856.15999999999
2021-07-11 20:18:33.434  INFO 21470 --- [-StreamThread-1] c.p.g.listener.GlobalStreamListener      : Key: [16/12/2006:17:29:00@1166310000000/1166313600000] and Value: 49354.20000000001
2021-07-11 20:18:33.434  INFO 21470 --- [-StreamThread-1] c.p.g.listener.GlobalStreamListener      : Key: [16/12/2006:17:30:00@1166310000000/1166313600000] and Value: 52001.90799999999
2021-07-11 20:18:33.435  INFO 21470 --- [-StreamThread-1] c.p.g.listener.GlobalStreamListener      : Key: [16/12/2006:17:31:00@1166310000000/1166313600000] and Value: 55747.140000000014
2021-07-11 20:18:33.435  INFO 21470 --- [-StreamThread-1] c.p.g.listener.GlobalStreamListener      : Key: [16/12/2006:17:32:00@1166310000000/1166313600000] and Value: 55455.63000000001
2021-07-11 20:18:33.435  INFO 21470 --- [-StreamThread-1] c.p.g.listener.GlobalStreamListener      : Key:
 */