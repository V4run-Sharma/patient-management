package org.vs.patientservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
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
import org.vs.patientservice.dto.request.CreatePatientRequestDto;
import org.vs.patientservice.dto.request.UpdatePatientRequestDto;
import org.vs.patientservice.dto.response.PatientResponseDto;
import org.vs.patientservice.service.PatientService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Patient", description = "API for managing patients")
@RestController
@RequestMapping("/patients")
public class PatientController {

  private final PatientService patientService;

  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }

  @GetMapping("/all")
  @Operation(summary = "Get all patients")
  public ResponseEntity<List<PatientResponseDto>> getAllPatients() {
    List<PatientResponseDto> patients = patientService.getAllPatients();
    return ResponseEntity.ok().body(patients);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get patient by ID")
  public ResponseEntity<PatientResponseDto> getPatientById(@Valid @PathVariable UUID id) {
    PatientResponseDto patient = patientService.getPatientById(id);
    return ResponseEntity.ok().body(patient);
  }

  @PostMapping("/register")
  @Operation(summary = "Register a new patient")
  public ResponseEntity<PatientResponseDto> registerPatient(
      @Validated({Default.class}) @RequestBody CreatePatientRequestDto createPatientRequestDto
  ) {
    PatientResponseDto patient = patientService.registerPatient(createPatientRequestDto);
    return ResponseEntity.ok().body(patient);
  }

  @PutMapping("/update/{id}")
  @Operation(summary = "Update patient information")
  public ResponseEntity<PatientResponseDto> updatePatient(
      @PathVariable UUID id,
      @Validated({Default.class}) @RequestBody UpdatePatientRequestDto createPatientRequestDto
  ) {
    PatientResponseDto updatedPatient = patientService.updatePatient(id, createPatientRequestDto);
    return ResponseEntity.ok().body(updatedPatient);
  }

  @DeleteMapping("/delete/{id}")
  @Operation(summary = "Delete a patient")
  public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
    patientService.deletePatient(id);
    return ResponseEntity.noContent().build();
  }
}
