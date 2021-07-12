package com.power_consumption_analysis.globalstreamsprocessor.util;


import com.power_consumption_analysis.globalstreamsprocessor.dto.GlobalCompiled;
import com.power_consumption_analysis.globalstreamsprocessor.dto.GlobalPower;
import com.power_consumption_analysis.globalstreamsprocessor.dto.GlobalVoltage;
import com.power_consumption_analysis.globalstreamsprocessor.dto.GlobalWattage;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RecordBuilder {
    public GlobalCompiled getGlobalCompiled(GlobalPower power, GlobalVoltage voltage) {

        GlobalCompiled globalCompiled = GlobalCompiled.builder()
                .date(power.getDate())
                .time(power.getTime())
                .current(power.getGlobalIntensity())
                .voltage(voltage.getVoltage())
                .build();

        return globalCompiled;
    }

    public GlobalWattage init() {
        GlobalWattage globalWattage = new GlobalWattage();
        globalWattage.setInstanceCount(0);
        globalWattage.setTotalWatts(0);
        globalWattage.setAvgWatts(0);
        return globalWattage;
    }

    public GlobalWattage aggregate(double watts, GlobalWattage aggValue) {
        GlobalWattage globalWattage = new GlobalWattage();
        globalWattage.setInstanceCount(aggValue.getInstanceCount() + 1);
        globalWattage.setTotalWatts(aggValue.getTotalWatts() + watts);
        globalWattage.setAvgWatts((aggValue.getTotalWatts() + watts) / (aggValue.getInstanceCount() + 1D));

        return globalWattage;
    }
}
