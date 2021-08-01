package com.power_consumption_analysis.submeterprocessor.service;

import com.power_consumption_analysis.submeterprocessor.dto.EnrichedS2Reading;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

@Service
//needs to update every second
@Log4j2
public class EventProcessorService {

    Queue<EnrichedS2Reading> queue;

    public EventProcessorService() {
        this.queue = new LinkedList<>();
    }

    public void handleEvent(EnrichedS2Reading reading) {

        this.queue.offer(reading);

    }

    public EnrichedS2Reading getProcessedEvent() {

      if(this.queue.size() > 30) {
         return this.queue.poll();
        }
        return null;
    }
}
