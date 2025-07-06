package com.project.analytics_service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsConsumer {
    private static Logger logger = LoggerFactory.getLogger(AnalyticsConsumer.class);

    @KafkaListener(topics = "patient-topic", groupId = "analytics_consumer_group")
    public void consumePatientStringData(String patientData) {
        logger.info("Received patient data: {}", patientData);
        // Process the patient data here
    }
}
