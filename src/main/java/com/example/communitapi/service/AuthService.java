package com.example.communitapi.service;

import com.example.communitapi.web.dto.auth.JwtRequest;
import com.example.communitapi.web.dto.auth.JwtResponse;
import org.springframework.stereotype.Service;

public interface AuthService {

    JwtResponse login (JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);
}
