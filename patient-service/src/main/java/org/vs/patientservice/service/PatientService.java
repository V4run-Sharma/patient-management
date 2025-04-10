package org.vs.patientservice.service;

import org.springframework.stereotype.Service;
import org.vs.patientservice.dto.request.CreatePatientRequestDto;
import org.vs.patientservice.dto.request.UpdatePatientRequestDto;
import org.vs.patientservice.dto.response.PatientResponseDto;
import org.vs.patientservice.exception.EmailAlreadyExistsException;
import org.vs.patientservice.exception.PatientNotFoundException;
import org.vs.patientservice.grpc.BillingServiceGrpcClient;
import org.vs.patientservice.model.Patient;
import org.vs.patientservice.repository.PatientRepo;
import org.vs.patientservice.utils.PatientMapper;

import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

  private final PatientRepo patientRepo;
  private final BillingServiceGrpcClient billingServiceGrpcClient;

  public PatientService(PatientRepo patientRepo, BillingServiceGrpcClient billingServiceGrpcClient) {
    this.patientRepo = patientRepo;
    this.billingServiceGrpcClient = billingServiceGrpcClient;
  }

  // Used stream API to convert List<Patient> to List<PatientResponseDto>
  public List<PatientResponseDto> getAllPatients() {
    List<Patient> patients = patientRepo.findAll();

    return patients.stream()
        .map(PatientMapper::convertToPatientResponseDto)
        .toList();
  }

  public PatientResponseDto getPatientById(UUID id) {
    Patient patient = patientRepo.findById(id).orElseThrow();

    return PatientMapper.convertToPatientResponseDto(patient);
  }

  public PatientResponseDto registerPatient(CreatePatientRequestDto createPatientRequestDto) {
    if (patientRepo.existsByEmail(createPatientRequestDto.getEmail())) {
      throw new EmailAlreadyExistsException("A patient with this email " +
          createPatientRequestDto.getEmail() + " already exists");
    }
    Patient savedPatient = patientRepo.save(PatientMapper.convertToPatient(createPatientRequestDto));

    billingServiceGrpcClient.createBillingAccount(
        savedPatient.getId().toString(),
        savedPatient.getName(),
        savedPatient.getEmail()
    );

    return PatientMapper.convertToPatientResponseDto(savedPatient);
  }

  public PatientResponseDto updatePatient(UUID id, UpdatePatientRequestDto updatePatientRequestDto) {
    if (!patientRepo.existsById(id)) {
      throw new PatientNotFoundException("Patient with id " + id + " does not exist");
    }
    Patient existingPatient = patientRepo.findById(id).orElseThrow();

    if (patientRepo.existsByEmail(updatePatientRequestDto.getEmail()) &&
        !existingPatient.getEmail().equals(updatePatientRequestDto.getEmail())) {
      throw new EmailAlreadyExistsException(
          "A patient with this email " + updatePatientRequestDto.getEmail() + " already exists");
    }
    Patient updatedPatient = PatientMapper.convertToPatient(existingPatient, updatePatientRequestDto);

    return PatientMapper.convertToPatientResponseDto(patientRepo.save(updatedPatient));
  }

  public void deletePatient(UUID id) {
    if (!patientRepo.existsById(id)) {
      throw new PatientNotFoundException("Patient with id " + id + " does not exist");
    }

    patientRepo.deleteById(id);
  }
}
