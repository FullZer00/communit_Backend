package com.example.communitapi.service;

import com.example.communitapi.entities.role.Role;
import com.example.communitapi.entities.userData.UserData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserDataService {
    UserData getById(long id);

    UserData getUserByEmail(String email);

    List<UserData> getAllUsers();

    List<UserData> getUsersByRole(Role role);

    UserData createUser(UserData userData);

    UserData updateUser(UserData userData);

    void deleteUser(long id);

    void deleteUser(UserData userData);
}
