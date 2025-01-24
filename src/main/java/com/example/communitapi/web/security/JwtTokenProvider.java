package com.example.communitapi.web.security;

import com.example.communitapi.entities.exceptions.AccessDeniedException;
import com.example.communitapi.entities.role.Role;
import com.example.communitapi.entities.userData.UserData;
import com.example.communitapi.service.UserDataService;
import com.example.communitapi.service.props.JwtProperties;
import com.example.communitapi.web.dto.auth.JwtResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    private final JwtUserDetailService userDetailsService;
    private final UserDataService userDataService;
    private SecretKey key;

    private static class Log {
        public void error(String msg, Exception ex) {
            System.out.println("Заглушка ошибки " + msg + ex.getMessage());
        }
    }

    private Log log;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
        this.log = new Log();
    }

    public String createAccessToken(long userId, String email, List<Role> roles) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());

        return Jwts.builder()
                .claim("email", email)
                .claim("id", userId)
                .claim("roles", resolveRoles(roles))
                .issuedAt(now)
                .expiration(validity)
                .signWith(key)
                .compact();
    }

    private List<String> resolveRoles(List<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    public String createRefreshToken(long userId, String email) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());

        return Jwts.builder()
                .claim("email", email)
                .claim("id", userId)
                .issuedAt(now)
                .expiration(validity)
                .signWith(key)
                .compact();
    }

    public JwtResponse refreshUserToken(String refreshToken) {
        JwtResponse jwtResponse = new JwtResponse();
        if (!validateToken(refreshToken)) {
            throw new AccessDeniedException();
        }
        long userId = Long.parseLong(getId(refreshToken));
        UserData user = userDataService.getById(userId);
        jwtResponse.setId(userId);
        jwtResponse.setEmail(user.getEmail());
        jwtResponse.setAccessToken(createAccessToken(userId, user.getEmail(), user.getRoles()));
        return jwtResponse;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    private String getId(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("id", String.class);
    }

    private String getUsername(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("email", String.class);
    }

    public Authentication getAuthentication(String token) {
        String email = getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByEmail(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
