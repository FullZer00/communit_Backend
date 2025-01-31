package com.example.communitapi.web.security;

import com.example.communitapi.entities.role.Role;
import com.example.communitapi.entities.userData.UserData;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class JwtEntityFactory {

    public static JwtEntity createJwtEntity(UserData user) {
        return new JwtEntity(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getRoles())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

}
