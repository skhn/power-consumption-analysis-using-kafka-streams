package com.power_consumption_analysis.globalstreamsprocessor.util;

import com.power_consumption_analysis.globalstreamsprocessor.dto.GlobalPower;
import com.power_consumption_analysis.globalstreamsprocessor.dto.GlobalVoltage;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Log4j2
@Component
public class TimeExtractorUtil {

    public long extractedTimeStamp(GlobalPower globalPower, GlobalVoltage globalVoltage) {
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss");
            Date date = null == globalPower ?
                    formatter.parse(globalVoltage.getDate() + ":" + globalVoltage.getTime()) :
                    formatter.parse(globalPower.getDate() + ":" + globalPower.getTime());
            Timestamp timestamp = new Timestamp(date.getTime());

            return timestamp.getTime();
        } catch (ParseException e) {
            log.error("Exception in extracting timestamp for windowing", e);
            return 0;
        }
    }

    public long extractedTimeStamp(String s) {
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss");
            Date date = formatter.parse(s);
            Timestamp timestamp = new Timestamp(date.getTime());
            return timestamp.getTime();
        } catch (ParseException e) {
            log.error("Exception in extracting timestamp for windowing", e);
            return 0;
        }
    }
}
