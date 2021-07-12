package com.power_consumption_analysis.globalstreamsprocessor.binding;

import com.power_consumption_analysis.globalstreamsprocessor.dto.GlobalPower;
import com.power_consumption_analysis.globalstreamsprocessor.dto.GlobalVoltage;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;


public interface PCAGlobalBinding {

    @Input("global-input-channel")
    KStream<String, GlobalPower> powerInputStream();

    @Input("voltage-input-channel")
    KStream<String, GlobalVoltage> voltageInputStream();
}
