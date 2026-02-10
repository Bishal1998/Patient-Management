package com.pm.patientservice.mapper;

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

    public static Patient toPatient(PatientResponseDto responseDto){
        return new Patient(

        );
    }
}
