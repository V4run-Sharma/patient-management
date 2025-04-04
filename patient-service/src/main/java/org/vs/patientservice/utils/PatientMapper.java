package org.vs.patientservice.utils;

import org.vs.patientservice.dto.request.CreatePatientRequestDto;
import org.vs.patientservice.dto.request.UpdatePatientRequestDto;
import org.vs.patientservice.dto.response.PatientResponseDto;
import org.vs.patientservice.model.Patient;

import java.time.LocalDate;
import java.util.Optional;

public class PatientMapper {

  public static PatientResponseDto convertToPatientResponseDto(Patient patient) {
    PatientResponseDto patientResponseDto = new PatientResponseDto();

    patientResponseDto.setId(patient.getId().toString());
    patientResponseDto.setName(patient.getName());
    patientResponseDto.setEmail(patient.getEmail());
    patientResponseDto.setAddress(patient.getAddress());
    patientResponseDto.setDateOfBirth(patient.getDateOfBirth());
    patientResponseDto.setDateOfRegistration(patient.getDateOfRegistration());

    return patientResponseDto;
  }

  public static Patient convertToPatient(CreatePatientRequestDto createPatientRequestDto) {
    Patient patient = new Patient();

    patient.setName(createPatientRequestDto.getName());
    patient.setEmail(createPatientRequestDto.getEmail());
    patient.setAddress(createPatientRequestDto.getAddress());
    patient.setDateOfBirth(LocalDate.parse(createPatientRequestDto.getDateOfBirth()));
    patient.setDateOfRegistration(LocalDate.parse(createPatientRequestDto.getDateOfRegistration()));

    return patient;
  }

  public static Patient convertToPatient(
      Patient existingPatient,
      UpdatePatientRequestDto updatePatientRequestDto
  ) {
    Patient updatedPatient = new Patient();

    updatedPatient.setId(existingPatient.getId()); // ID remains unchanged
    updatedPatient.setName(Optional.ofNullable(updatePatientRequestDto.getName()).orElse(existingPatient.getName()));
    updatedPatient.setEmail(Optional.ofNullable(updatePatientRequestDto.getEmail()).orElse(existingPatient.getEmail()));
    updatedPatient.setAddress(Optional.ofNullable(updatePatientRequestDto.getAddress()).orElse(existingPatient.getAddress()));
    updatedPatient.setDateOfBirth(
        Optional.ofNullable(updatePatientRequestDto.getDateOfBirth())
            .map(LocalDate::parse)
            .orElse(existingPatient.getDateOfBirth())
    );
    updatedPatient.setDateOfRegistration(
        Optional.ofNullable(updatePatientRequestDto.getDateOfRegistration())
            .map(LocalDate::parse)
            .orElse(existingPatient.getDateOfRegistration())
    );

    return updatedPatient;
  }
}
