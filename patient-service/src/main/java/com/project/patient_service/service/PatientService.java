package com.project.patient_service.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.project.patient_service.dto.PatientRequestDTO;
import com.project.patient_service.dto.PatientResponseDTO;
import com.project.patient_service.exception.EmailAlreadyExistsException;
import com.project.patient_service.exception.PatientNotFoundException;
import com.project.patient_service.mapper.PatientMapper;
import com.project.patient_service.model.Patient;
import com.project.patient_service.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     * Retrieves all patients from the repository and maps them to DTOs.
     *
     * @return a list of PatientResponseDTOs representing all patients.
     */
    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponseDTO> patientResponseDTOs = patients.stream()
                .map(patient -> PatientMapper.toDTO(patient))
                .toList();

        return patientResponseDTOs;
    }

    /**
     * Retrieves a patient by ID and maps it to a DTO.
     *
     * @param id the UUID of the patient to retrieve.
     * @return a PatientResponseDTO representing the patient.
     */
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "A patient with this email " + "already exists"
                            + patientRequestDTO.getEmail());
        }

        Patient newPatient = patientRepository.save(
                PatientMapper.toModel(patientRequestDTO));

        return PatientMapper.toDTO(newPatient);
    }

    @Transactional
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    /**
     * Updates an existing patient by ID and maps the updated patient to a DTO.
     *
     * @param id                the UUID of the patient to update.
     * @param patientRequestDTO the DTO containing the updated patient information.
     * @return a PatientResponseDTO representing the updated patient.
     */
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("Patient not found with ID: " + id));

        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),
                id)) {
            throw new EmailAlreadyExistsException(
                    "A patient with this email " + "already exists"
                            + patientRequestDTO.getEmail());
        }

        patient.setName(patientRequestDTO.getName());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);
    }

    /**
     * Deletes a patient by ID.
     *
     * @param id the UUID of the patient to delete.
     */
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new PatientNotFoundException("Patient not found with ID: " + id);
        }
        patientRepository.deleteById(id);
    }

    /**
     * Retrieves a patient by ID and maps it to a DTO.
     *
     * @param id the UUID of the patient to retrieve.
     * @return a PatientResponseDTO representing the patient.
     */
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }
}
