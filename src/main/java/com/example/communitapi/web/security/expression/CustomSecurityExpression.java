package com.example.communitapi.web.security.expression;

import com.example.communitapi.service.ClientService;
import com.example.communitapi.web.security.JwtEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("customSecurityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final ClientService clientService;

    public boolean canAccessUser(long id, String... roles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        JwtEntity jwtEntity = (JwtEntity) authentication.getPrincipal();
        Long userId = jwtEntity.getId();

        return userId.equals(id) || hasAnyRoles(authentication, roles);
    }

    private boolean hasAnyRoles(Authentication authentication, String... roles) {
        for (String role : roles) {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(role))) {
                return true;
            }
        }
        return false;
    }

    public boolean canAccessProject(long projectId, String... roles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        JwtEntity jwtEntity = (JwtEntity) authentication.getPrincipal();
        long userId = jwtEntity.getId();


        return clientService.isProjectOwner(projectId, userId)  || hasAnyRoles(authentication, roles);
    }
}
