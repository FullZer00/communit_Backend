package com.example.communitapi.web.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {

    public JwtResponse() {

    }

    private long id;
    private String email;
    private String accessToken;
    private String refreshToken;

}
