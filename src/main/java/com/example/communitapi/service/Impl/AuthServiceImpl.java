package com.example.communitapi.service.Impl;

import com.example.communitapi.entities.userData.UserData;
import com.example.communitapi.service.AuthService;
import com.example.communitapi.service.UserDataService;
import com.example.communitapi.web.dto.auth.JwtRequest;
import com.example.communitapi.web.dto.auth.JwtResponse;
import com.example.communitapi.web.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDataService userDataService;

    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        UserData userData = userDataService.getUserByEmail(loginRequest.getEmail());
        return new JwtResponse(
                userData.getId(),
                userData.getEmail(),
                jwtTokenProvider.createAccessToken(userData.getId(), userData.getEmail(), userData.getRoles()),
                jwtTokenProvider.createRefreshToken(userData.getId(), userData.getEmail())
        );
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserToken(refreshToken);
    }
}
