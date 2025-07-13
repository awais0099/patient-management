package com.project.analytics_service.controller;

import com.project.analytics_service.model.Patient;
import com.project.analytics_service.model.Patient;
import com.project.analytics_service.service.AnalyticsDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsDataService analyticsDataService;

    /**
     * Retrieves all patient events stored in the analytics database.
     * @return A list of PatientEvent objects.
     */
    @GetMapping("/patient-events")
    public ResponseEntity<List<Patient>> getAllPatientEvents() {
        List<Patient> events = analyticsDataService.getAllPatientEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    /**
     * Retrieves patient events for a specific patient ID.
     * @param patientId The ID of the patient.
     * @return A list of PatientEvent objects for the given patient ID.
     */
    @GetMapping("/patient-events/patient/{patientId}")
    public ResponseEntity<List<Patient>> getPatientEventsByPatientId(@PathVariable Long patientId) {
        List<Patient> events = analyticsDataService.getPatientEventsByPatientId(patientId);
        if (events.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    /**
     * Retrieves patient events by a specific event type.
     * @param eventType The type of event (e.g., "PATIENT_CREATED_OR_UPDATED", "PATIENT_DELETED").
     * @return A list of PatientEvent objects for the given event type.
     */
    @GetMapping("/patient-events/type/{eventType}")
    public ResponseEntity<List<Patient>> getPatientEventsByEventType(@PathVariable String eventType) {
        List<Patient> events = analyticsDataService.getPatientEventsByEventType(eventType);
        if (events.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}

