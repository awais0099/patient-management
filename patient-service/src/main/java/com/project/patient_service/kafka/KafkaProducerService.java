package com.project.patient_service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        logger.info("Producing message: {} to topic: {}", message, topic);
        try {
            kafkaTemplate.send(topic, message);
        } catch (Exception e) {
            logger.error("Error sending message to Kafka topic: {}", topic, e);
        }
    }
}
