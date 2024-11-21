package com.example.communitapi.service;

import com.example.communitapi.web.dto.userData.UserDataDto;

public interface RegisterService {

    void sendConfirmationEmail(UserDataDto userData);

    UserDataDto registerUser(UserDataDto userData);

}
