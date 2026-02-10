package com.pm.patientservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PatientRequestDto(
        @NotBlank(message = "Name can not be blank.")
        @Size(max = 100, message = "Name can not exceed 100 characters.")
        String name,

        @NotBlank(message = "Email can not be blank.")
        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "Address can not be blank.")
        String address,

        @NotNull(message = "Date of birth can not be blank.")
        LocalDate dateOfBirth,

        @NotNull(message = "Registered date can not be blank.")
        LocalDate registeredDate
) {
}
