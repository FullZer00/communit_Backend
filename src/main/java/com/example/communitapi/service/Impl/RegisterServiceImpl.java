package com.example.communitapi.service.Impl;

import com.example.communitapi.entities.userData.UserData;
import com.example.communitapi.service.PasswordService;
import com.example.communitapi.service.RegisterService;
import com.example.communitapi.service.UserDataService;
import com.example.communitapi.web.dto.userData.UserDataDto;
import com.example.communitapi.web.dto.userData.UserDataWithPasswordDto;
import com.example.communitapi.web.mappers.UserDataMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserDataMapper userDataMapper;
    private final UserDataService userDataService;
    private final PasswordService passwordService;

    @Override
    public void sendConfirmationEmail(UserDataDto userData) {

    }

    @Override
    public UserDataDto registerUser(UserDataDto userDataDto) {
        UserData userData  = userDataMapper.fromDto(userDataDto);
        UserData createdUserData = userDataService.createUser(userData);
        return userDataMapper.toDto(createdUserData);
    }

    @Override
    public UserDataDto registerUserWithPassword(UserDataWithPasswordDto userDataDto) {
        if (userDataDto.getPassword() == null || userDataDto.getPassword().isEmpty()) {
            throw new RuntimeException("Password is empty");
        }

        UserData userData = userDataMapper.fromDto(userDataDto);
        String hashPassword = passwordService.hashPassword(userDataDto.getPassword());
        userData.setPassword(hashPassword);
        UserData createdUserData = userDataService.createUser(userData);

        return userDataMapper.toDto(createdUserData);
    }
}
