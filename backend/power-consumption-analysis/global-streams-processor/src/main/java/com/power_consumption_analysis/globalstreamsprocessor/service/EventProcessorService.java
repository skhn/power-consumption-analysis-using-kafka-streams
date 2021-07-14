package com.power_consumption_analysis.globalstreamsprocessor.service;

import com.power_consumption_analysis.globalstreamsprocessor.dto.GlobalWattage;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Service
//needs to update every minute
@Log4j2
public class EventProcessorService {

    HashMap<String, GlobalWattage> globalWattageHashMap;

    public EventProcessorService() {
        this.globalWattageHashMap = new HashMap<>();
    }

    public void handleEvent(GlobalWattage wattage) {

        globalWattageHashMap.put(wattage.getDateTime(), wattage);

    }

    public GlobalWattage getProcessedEvent() {

        if (globalWattageHashMap.size() > 3) {
            Map.Entry<String, GlobalWattage> firstRecord = Collections.min(globalWattageHashMap.entrySet(),
                    Comparator.comparing(Map.Entry::getKey));
            globalWattageHashMap.remove(firstRecord.getKey());
            log.info(firstRecord);
            return firstRecord.getValue();
        }
        return null;
    }
}
