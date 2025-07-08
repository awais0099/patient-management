package com.project.patient_service.kafka;

import com.project.patient_service.model.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaProducerService {
    private Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private KafkaTemplate<String, byte[]> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, Patient patient) {
        logger.info("Producing patient: {} to topic: {}", patient, topic);

        PatientEvent patientEvent = PatientEvent.newBuilder()
                .setId(patient.getId())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setAddress(patient.getAddress())
                .setDateOfBirth(patient.getDateOfBirth().toString())
                .setRegisteredDate(patient.getRegisteredDate().toString())
                .setEventType("PATIENT_CREATED")
                .build();
        try {
            kafkaTemplate.send(topic, patientEvent.toByteArray());
        } catch (Exception e) {
            logger.error("Error sending message to Kafka topic: {}", topic, e);
        }
    }
}
