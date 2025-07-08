package com.project.analytics_service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import patient.events.PatientEvent;

@Component
public class AnalyticsConsumer {
    private static Logger logger = LoggerFactory.getLogger(AnalyticsConsumer.class);

    @KafkaListener(topics = "patient-topic", groupId = "analytics_consumer_group")
    public void consumePatientEvent(byte[] event) {
        logger.info("Received patient data: {}", event);
        // Process the patient data here
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);

            // ... perform any business related to analytics here

            logger.info("Received Patient Event: [id={},name={},email={}]",
                    patientEvent.getId(),
                    patientEvent.getName(),
                    patientEvent.getEmail()
            );
        } catch (Exception e) {
            logger.error("Error deserializing event {}", e.getMessage());
        }
    }
}
