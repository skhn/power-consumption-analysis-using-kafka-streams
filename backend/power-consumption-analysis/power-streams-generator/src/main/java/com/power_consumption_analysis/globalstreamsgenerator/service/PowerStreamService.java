package com.power_consumption_analysis.globalstreamsgenerator.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.power_consumption_analysis.globalstreamsgenerator.dto.GlobalPower;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@Log4j2
public class PowerStreamService {

    @Autowired
    private KafkaTemplate<String, GlobalPower> template;

    @Value("${application.config.kafka.topic-name}")
    private String topic;

    @Value("${application.config.data.filepath}")
    private String filePath;

    private MappingIterator<GlobalPower> globalPowerMappingIterator;

    public void sendRecordToTopic() throws DataRetrievalFailureException, IOException {

        final InputStream csvDataFile;

        try {

            csvDataFile = new FileInputStream(getClass().getClassLoader().getResource(filePath).getFile());
            CsvMapper csvMapper = new CsvMapper();

            CsvSchema schema = csvMapper
                    .typedSchemaFor(GlobalPower.class)
                    .withHeader()
                    .withColumnReordering(true);


            globalPowerMappingIterator = csvMapper.readerFor(GlobalPower.class)
                    .with(schema)
                    .readValues(csvDataFile);
        } catch (IOException | NullPointerException e) {
            log.error("IOException Caught while trying to read csv file");
            throw new DataRetrievalFailureException(e.getMessage());
        }

        try {
            Iterable<GlobalPower> iterable = () -> globalPowerMappingIterator;
            Stream<GlobalPower> iterableStreamWrapper = StreamSupport.stream(iterable.spliterator(), false);

            iterableStreamWrapper
                    .skip(1000)
                    .forEach(entry -> {
                                final String key = entry.getDate() + ":" + entry.getTime();
                                log.info(String.format("Producing record with key: %s, value: %s to the topic: %s", key, entry.toString(), topic));
                                template.send(topic, key, entry);
                                try {
                                    //sending every minute record, every second for demonstration purposes
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    log.error("Issue in processing stream thread: ", e);
                                }
                            }
                    );
        } catch (Exception e) {
            log.error(e);
            throw new DataRetrievalFailureException("End of Records");
        } finally {
            csvDataFile.close();
        }
    }
}
