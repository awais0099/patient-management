package com.project.analytics_service.kafka;

import com.project.analytics_service.model.Patient;
import com.project.analytics_service.service.AnalyticsDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import patient.events.PatientEvent;

import java.time.LocalDateTime;

@Component
public class AnalyticsConsumer {
    private static Logger logger = LoggerFactory.getLogger(AnalyticsConsumer.class);

    @Autowired
    private AnalyticsDataService analyticsDataService; // Inject the analytics data service

    @KafkaListener(topics = "patient-topic", groupId = "analytics_consumer_group")
    public void consumePatientEvent(byte[] event) {
        logger.info("Received patient data: {}", event);

        // Process the patient data here
        try {
            Long patientId = null; // Default value
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            patientId = patientEvent.getId();

            logger.info("Received Patient Event: [id={},name={},email={}]",
                    patientEvent.getId(),
                    patientEvent.getName(),
                    patientEvent.getEmail()
            );

            if (patientEvent.getId() != 0) {
                Patient patient = new Patient();
                patient.setPatientId(patientEvent.getId());
                patient.setEventType(patientEvent.getEventType());
                patient.setEventTimestamp(LocalDateTime.now()); 
                analyticsDataService.savePatientEvent(patient);
                logger.info("Saved patient event to database: Patient ID={}, Event Type={}", patientId, patientEvent.getEventType());
            } else {
                logger.warn("Received Patient Event with ID 0, skipping processing.");
            }
        } catch (Exception e) {
            logger.error("Error deserializing event {}", e.getMessage());
        }
    }
}
