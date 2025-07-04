package com.project.patient_service.mapper;

import com.project.patient_service.dto.PatientRequestDTO;
import com.project.patient_service.dto.PatientResponseDTO;
import com.project.patient_service.model.Patient;

import java.time.LocalDate;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient) {
        PatientResponseDTO dto = new PatientResponseDTO();
        dto.setId(patient.getId().toString());
        dto.setName(patient.getName());
        dto.setEmail(patient.getEmail());
        dto.setAddress(patient.getAddress());
        dto.setDateOfBirth(patient.getDateOfBirth().toString());
        return dto;
    }

    public static Patient toModel(PatientRequestDTO patientRequestDTO) {
        Patient patient = new Patient();
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        if (patientRequestDTO.getRegisteredDate() != null) {
            patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));
        }
        return patient;
    }
}
