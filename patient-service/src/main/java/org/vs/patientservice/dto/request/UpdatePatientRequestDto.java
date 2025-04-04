package org.vs.patientservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdatePatientRequestDto{
  @Size(max = 100, message = "Name should be less than 100 characters")
  private String name;

  @Email(message = "Email should be valid")
  @Size(max = 100, message = "Email should be less than 100 characters")
  private String email;

  private String address;

  private String dateOfBirth;

  private String dateOfRegistration;
}
