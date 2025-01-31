package com.example.communitapi.web.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class JwtRequest {

    @NotEmpty(message = "Email must be not null.")
    private String email;

    @NotEmpty(message = "Password must be not null.")
    private String password;

}
