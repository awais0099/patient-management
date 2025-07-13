package com.project.analytics_service.service;

import com.project.analytics_service.model.Patient;
import com.project.analytics_service.repository.PatientEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnalyticsDataService {

    @Autowired
    private PatientEventRepository patientEventRepository;

    /**
     * Saves a patient event to the database.
     * @param patientEvent The PatientEvent object to save.
     * @return The saved PatientEvent object.
     */
    @Transactional
    public Patient savePatientEvent(Patient patientEvent) {
        return patientEventRepository.save(patientEvent);
    }

    /**
     * Retrieves all patient events from the database.
     * @return A list of all PatientEvent objects.
     */
    public List<Patient> getAllPatientEvents() {
        return patientEventRepository.findAll();
    }

    /**
     * Retrieves patient events by a specific patient ID.
     * @param patientId The ID of the patient.
     * @return A list of PatientEvent objects for the given patient ID.
     */
    public List<Patient> getPatientEventsByPatientId(Long patientId) {
        return patientEventRepository.findByPatientId(patientId);
    }

    /**
     * Retrieves patient events by a specific event type.
     * @param eventType The type of event (e.g., "PATIENT_CREATED").
     * @return A list of PatientEvent objects for the given event type.
     */
    public List<Patient> getPatientEventsByEventType(String eventType) {
        return patientEventRepository.findByEventType(eventType);
    }
}
