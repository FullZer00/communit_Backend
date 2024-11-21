package com.example.communitapi.web.controllers;

import com.example.communitapi.service.AuthService;
import com.example.communitapi.service.RegisterService;
import com.example.communitapi.service.UserDataService;
import com.example.communitapi.web.dto.auth.JwtRequest;
import com.example.communitapi.web.dto.auth.JwtResponse;
import com.example.communitapi.web.dto.userData.UserDataDto;
import com.example.communitapi.web.dto.validation.OnCreate;
import com.example.communitapi.web.mappers.UserDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.path}/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;
    private final RegisterService regService;
    private final UserDataService userDataService;

    private final UserDataMapper userMapper;

    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public UserDataDto register(@Validated(OnCreate.class) @RequestBody UserDataDto userDataDto) {
        return regService.registerUser(userDataDto);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@Validated @RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }


}
