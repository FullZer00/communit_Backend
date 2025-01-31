package com.example.communitapi.web.controllers;

import com.example.communitapi.service.AuthService;
import com.example.communitapi.service.RegisterService;
import com.example.communitapi.web.dto.auth.JwtRequest;
import com.example.communitapi.web.dto.auth.JwtResponse;
import com.example.communitapi.web.dto.userData.UserDataDto;
import com.example.communitapi.web.dto.userData.UserDataWithPasswordDto;
import com.example.communitapi.web.dto.validation.OnCreate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Auth Controller", description = "Auth API")
public class AuthController {

    private final AuthService authService;
    private final RegisterService regService;

    @PostMapping("/login")
    @Operation(summary = "Login user")
    public JwtResponse login(@Validated @RequestBody JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    @Operation(summary = "Registration user whihtout password")
    public UserDataDto register(@Validated(OnCreate.class) @RequestBody UserDataDto userDataDto) {
        return regService.registerUser(userDataDto);
    }

    @PostMapping("/register/password")
    @Operation(summary = "Registration user with password")
    public UserDataDto registerWithPassword(@Validated(OnCreate.class) @RequestBody UserDataWithPasswordDto userDataDto) {
        return regService.registerUserWithPassword(userDataDto);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh token")
    public JwtResponse refresh(@Validated @RequestBody String token) {
        return authService.refresh(token);
    }


}
