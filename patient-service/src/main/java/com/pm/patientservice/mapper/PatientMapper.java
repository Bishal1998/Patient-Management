package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import com.pm.patientservice.model.Patient;

public class PatientMapper {

    public static PatientResponseDto toDto(Patient patient){

        return new PatientResponseDto(
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                patient.getAddress(),
                patient.getDateOfBirth()
        );
    }

    public static Patient toPatient(PatientRequestDto request){
        Patient newPatient = new Patient();

        newPatient.setName(request.name());
        newPatient.setEmail(request.email());
        newPatient.setAddress(request.address());
        newPatient.setDateOfBirth(request.dateOfBirth());
        newPatient.setRegisteredDate(request.registeredDate());

        return newPatient;
    }
}
