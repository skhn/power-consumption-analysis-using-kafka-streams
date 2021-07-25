package com.power_consumption_analysis.submeterprocessor.binding;

import com.kinandcarta.usfoods.kafka.submeter2.Mappings;
import com.kinandcarta.usfoods.kafka.submeter2.Submeter2;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.cloud.stream.annotation.Input;

public interface S2EventBinding {

    @Input("s2-input-channel")
    KStream<String, Submeter2> s2InputStream();

    @Input("mapping-input-channel")
    KTable<String, Mappings> s2MappingTableStream();

}
