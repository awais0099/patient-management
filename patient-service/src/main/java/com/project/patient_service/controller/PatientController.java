package com.project.patient_service.controller;

import billing.BillRequest;
import billing.BillResponse;
import com.project.patient_service.dto.PatientRequestDTO;
import com.project.patient_service.dto.PatientResponseDTO;
import com.project.patient_service.dto.validators.CreatePatientValidationGroup;
import com.project.patient_service.grpc.client.BillingGrpcClient;
import com.project.patient_service.mapper.PatientMapper;
import com.project.patient_service.model.Patient;
import com.project.patient_service.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient Management", description = "API Operations related to patient management")
public class PatientController {

    private final PatientService patientService;
    @Autowired
    private BillingGrpcClient billingGrpcClient;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @Operation(summary = "Get all patients", description = "Retrieve a list of all patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        List<PatientResponseDTO> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);
    }

    @PostMapping
    @Operation(summary = "Create a new patient", description = "Add a new patient to the system")
    public ResponseEntity<PatientResponseDTO> savePatient(@Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        Patient patient = PatientMapper.toModel(patientRequestDTO);
        Patient savedPatient = patientService.savePatient(patient);
        LocalDate today = LocalDate.now();

        BillResponse billResponse = billingGrpcClient.CreateBill(
                BillRequest.newBuilder()
                        .setPatientId(savedPatient.getId())
                        .setAmount(100.0)
                        .setServiceType("Health care")
                        .setBillDate(today.toString())
                        .build()
        );

        PatientResponseDTO patientResponseDTO = PatientMapper.toDTO(savedPatient);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing patient", description = "Modify the details of an existing patient")
    public ResponseEntity<PatientResponseDTO> updatePatient(
            @PathVariable Long id,
            @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO
    ) {

        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id,
                patientRequestDTO);

        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a patient", description = "Remove a patient from the system")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}