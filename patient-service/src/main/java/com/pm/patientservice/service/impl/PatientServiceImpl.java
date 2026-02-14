package com.pm.patientservice.service.impl;

import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.grpc.BillingServiceGrpcClient;
import lombok.RequiredArgsConstructor;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import org.springframework.stereotype.Service;
import com.pm.patientservice.repository.PatientRepository;
import com.pm.patientservice.service.PatientService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;

    @Override
    public List<PatientResponseDto> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();

        return patients.stream().map(PatientMapper::toDto).toList();
    }

    @Override
    public PatientResponseDto createPatient(PatientRequestDto patientRequestDto) {
        patientRepository.findByEmail(patientRequestDto.email()).ifPresent(p -> {
            throw new EmailAlreadyExistsException("Email already exists: " + patientRequestDto.email());
        });

        Patient newPatient = PatientMapper.toPatient(patientRequestDto);

        Patient savedPatient = patientRepository.save(newPatient);

        billingServiceGrpcClient.createBillingAccount(savedPatient.getId().toString(), savedPatient.getName(), savedPatient.getEmail());

        return PatientMapper.toDto(savedPatient);
    }

    @Override
    public PatientResponseDto updatePatient(UUID id, PatientRequestDto patientRequestDto) {

        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient with id: " + id + " not found."));

        patientRepository.findByEmail(patientRequestDto.email()).filter(p-> !p.getId().equals(id)).ifPresent(p -> {
            throw new EmailAlreadyExistsException("Email already exists: " + patientRequestDto.email());
        });

            patient.setName(patientRequestDto.name());
            patient.setEmail(patientRequestDto.email());
            patient.setAddress(patientRequestDto.address());
            patient.setDateOfBirth(patientRequestDto.dateOfBirth());

        Patient updatedPatient = patientRepository.save(patient);

        return PatientMapper.toDto(updatedPatient);
    }

    @Override
    public void deletePatient(UUID id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient with id: " + id + " not found."));

        patientRepository.deleteById(id);
    }
}
