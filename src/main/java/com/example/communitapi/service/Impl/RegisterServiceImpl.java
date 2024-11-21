package com.example.communitapi.service.Impl;

import com.example.communitapi.entities.userData.UserData;
import com.example.communitapi.service.RegisterService;
import com.example.communitapi.service.UserDataService;
import com.example.communitapi.web.dto.userData.UserDataDto;
import com.example.communitapi.web.mappers.UserDataMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserDataMapper userDataMapper;
    private final UserDataService userDataService;

    @Override
    public void sendConfirmationEmail(UserDataDto userData) {

    }

    @Override
    public UserDataDto registerUser(UserDataDto userDataDto) {
        UserData userData  = userDataMapper.fromDto(userDataDto);
        UserData createdUserData = userDataService.createUser(userData);
        return userDataMapper.toDto(createdUserData);
    }
}
