package com.example.communitapi.web.security;

import com.example.communitapi.entities.userData.UserData;
import com.example.communitapi.entities.worker.Worker;
import com.example.communitapi.service.UserDataService;
import com.example.communitapi.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailService implements UserDetailsService {

    private final UserDataService userDataService;

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        System.out.println("original method");
        UserData userData = userDataService.getUserByEmail(email);
        return JwtEntityFactory.createJwtEntity(userData);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("override method");
        UserData userData = userDataService.getUserByEmail(username);
        return JwtEntityFactory.createJwtEntity(userData);
    }
}
