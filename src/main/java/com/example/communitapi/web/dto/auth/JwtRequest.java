package com.example.communitapi.web.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JwtRequest {

    @NotNull(message = "Email must be not null.")
    private String email;

    @NotNull(message = "Password must be not null.")
    private String password;

}
