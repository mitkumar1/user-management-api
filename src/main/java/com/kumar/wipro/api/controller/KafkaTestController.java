package com.kumar.wipro.api.controller;

import com.kumar.wipro.api.kafka.KafkaProducerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaTestController {
    private final KafkaProducerService producerService;

    public KafkaTestController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping("/kafka/send")
    public String sendToKafka(@RequestParam String message) {
        producerService.sendMessage("user-events", message);
        return "Message sent to Kafka: " + message;
    }
}
