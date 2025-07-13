package com.project.analytics_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "patient_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long patientId;

    @Column(nullable = false)
    private String eventType; // e.g., "PATIENT_CREATED", "PATIENT_UPDATED", "PATIENT_DELETED"

    @Column(nullable = false)
    private LocalDateTime eventTimestamp; // When the event was recorded by analytics service

    // You could add more fields here based on what you want to analyze,
    // e.g., String rawMessage; to store the original Kafka message.
}
