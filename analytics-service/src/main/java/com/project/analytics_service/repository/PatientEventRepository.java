package com.project.analytics_service.repository;

import com.project.analytics_service.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientEventRepository extends JpaRepository<Patient, Long> {
    // Custom query to find events by patient ID
    List<Patient> findByPatientId(Long patientId);

    // Custom query to find events by event type
    List<Patient> findByEventType(String eventType);
}
