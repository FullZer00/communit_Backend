package com.example.communitapi.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Login response")
public class JwtResponse {

    public JwtResponse() {}

    @Schema(description = "User id", example = "1")
    private long id;
    @Schema(description = "User email", example = "Test@gmail.com")
    private String email;
    @Schema(description = "Access token")
    private String accessToken;
    @Schema(description = "refresh token")
    private String refreshToken;

}
