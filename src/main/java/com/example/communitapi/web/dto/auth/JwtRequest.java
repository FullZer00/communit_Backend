package com.example.communitapi.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(description = "Login DTO")
public class JwtRequest {

    @Schema(description = "User email", example = "Test@gmail.com")
    @NotEmpty(message = "Email must be not null.")
    private String email;

    @Schema(description = "User password", example = "P@ssw0rd!")
    @NotEmpty(message = "Password must be not null.")
    private String password;

}
