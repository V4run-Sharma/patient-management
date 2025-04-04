package org.vs.patientservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreatePatientRequestDto {
  @NotBlank(message = "Name is mandatory")
  @Size(max = 100, message = "Name should be less than 100 characters")
  private String name;

  @NotBlank(message = "Email is mandatory")
  @Email(message = "Email should be valid")
  @Size(max = 100, message = "Email should be less than 100 characters")
  private String email;

  @NotBlank(message = "Address is mandatory")
  private String address;

  @NotNull(message = "Date of Birth is mandatory")
  private String dateOfBirth;

  @NotNull(message = "Date of Registration is mandatory")
  private String dateOfRegistration;
}
