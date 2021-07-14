package com.power_consumption_analysis.submeter2streamsgenerator.service;


import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.kinandcarta.usfoods.kafka.submeter2.Submeter2;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@Log4j2
public class Submeter2StreamService {

    @Autowired
    private KafkaTemplate<String, Submeter2> template;

    @Value("${application.config.kafka.topic-name}")
    private String topic;

    @Value("${application.config.data.filepath}")
    private String filePath;

    private MappingIterator<Submeter2> submeter2MappingIterator;

    private Random rand;

    public Submeter2StreamService() {
        this.rand = new Random();
    }

    public void sendRecordToTopic() throws DataRetrievalFailureException, IOException {

        final InputStream csvDataFile;

        try {

            csvDataFile = new FileInputStream(getClass().getClassLoader().getResource(filePath).getFile());
            CsvMapper csvMapper = new CsvMapper();

            CsvSchema schema = csvMapper
                    .typedSchemaFor(Submeter2.class)
                    .withHeader()
                    .withColumnReordering(true);


            submeter2MappingIterator = csvMapper.readerFor(Submeter2.class)
                    .with(schema)
                    .readValues(csvDataFile);
        } catch (IOException | NullPointerException e) {
            log.error("IOException Caught while trying to read csv file");
            throw new DataRetrievalFailureException(e.getMessage());
        }

        try {
            Iterable<Submeter2> iterable = () -> submeter2MappingIterator;
            Stream<Submeter2> iterableStreamWrapper = StreamSupport.stream(iterable.spliterator(), false);

            iterableStreamWrapper
                    .skip(1000)
                    .forEach(entry -> {
                                final String key = entry.getDate() + ":" + entry.getTime();
                                log.info(String.format("Producing record with key: %s, value: %s to the topic: %s", key, entry.toString(), topic));
                                template.send(topic, key, entry);
                                try {
                                    //sending every minute record, every second for demonstration purposes
                                    Thread.sleep(750 + rand.nextInt(500));
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
