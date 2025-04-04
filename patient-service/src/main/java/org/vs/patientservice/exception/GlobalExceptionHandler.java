package org.vs.patientservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // This annotation is used to handle exceptions globally outside the controller
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  // MethodArgumentNotValidException is thrown when the request body fails validation
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();

    // Binding result contains the validation errors
    // FieldErrors contains the field name and the error message
    ex.getBindingResult().getFieldErrors().forEach(error ->
        errors.put(error.getField(), error.getDefaultMessage())
    );
    log.warn("MethodArgumentNotValidException: {}", errors);

    return ResponseEntity.badRequest().body(errors);
  }

  // Used to handle EmailAlreadyExistsException
  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<Map<String, String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {

    Map<String, String> errors = new HashMap<>();

    errors.put("errorMessage", ex.getMessage());
    log.warn("EmailAlreadyExistsException: {}", errors);

    return ResponseEntity.badRequest().body(errors);
  }

  // Used to handle PatientNotFoundException
  @ExceptionHandler(PatientNotFoundException.class)
  public ResponseEntity<Map<String, String>> handlePatientNotFoundException(PatientNotFoundException ex) {

    Map<String, String> errors = new HashMap<>();

    errors.put("errorMessage", ex.getMessage());
    log.warn("PatientNotFoundException: {}", errors);

    return ResponseEntity.badRequest().body(errors);
  }
}
