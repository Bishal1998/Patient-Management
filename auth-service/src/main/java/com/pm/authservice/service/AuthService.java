package com.pm.authservice.service;

import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.dto.LoginResponseDTO;

import java.util.Optional;

public interface AuthService {
    public Optional<String> login(LoginRequestDTO loginRequestDTO);
}
