package com.example.communitapi.service;

import com.example.communitapi.web.dto.userData.UserDataDto;
import com.example.communitapi.web.dto.userData.UserDataWithPasswordDto;

public interface RegisterService {

    void sendConfirmationEmail(UserDataDto userData);

    UserDataDto registerUser(UserDataDto userData);

    UserDataDto registerUserWithPassword(UserDataWithPasswordDto userData);

}
