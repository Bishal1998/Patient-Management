package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDto;
import com.pm.patientservice.dto.PatientResponseDto;
import com.pm.patientservice.dto.validators.CreatePatientValidationGroup;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.pm.patientservice.service.PatientService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
@Tag(name = "Patient", description = "API for Patient Management")
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    @Operation(summary = "Get all Patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients(){
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @PostMapping
    @Operation(summary = "Create a Patient")
    public ResponseEntity<PatientResponseDto> createPatient(@Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDto requestDto){
        return new ResponseEntity<>(patientService.createPatient(requestDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Patient")
    public ResponseEntity<PatientResponseDto> updatePatient(@PathVariable UUID id, @Validated({Default.class}) @RequestBody PatientRequestDto patientRequestDto){
        return ResponseEntity.ok(patientService.updatePatient(id, patientRequestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id){
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
