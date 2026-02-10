package com.pm.patientservice.service.impl;

import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import lombok.RequiredArgsConstructor;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import org.springframework.stereotype.Service;
import com.pm.patientservice.repository.PatientRepository;
import com.pm.patientservice.service.PatientService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public List<PatientResponseDto> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();

        return patients.stream().map(PatientMapper::toDto).toList();
    }

    @Override
    public PatientResponseDto createPatient(PatientRequestDto patientRequestDto) {
        patientRepository.findByEmail(patientRequestDto.email()).ifPresent(p -> {
            throw new RuntimeException("Email already exists");
        });

        Patient newPatient = PatientMapper.toPatient(patientRequestDto);

        Patient savedPatient = patientRepository.save(newPatient);

        return PatientMapper.toDto(savedPatient);
    }
}
