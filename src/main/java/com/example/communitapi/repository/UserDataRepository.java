package com.example.communitapi.repository;

import com.example.communitapi.entities.role.Role;
import com.example.communitapi.entities.userData.UserData;

import java.util.List;
import java.util.Optional;

public interface UserDataRepository {

    Optional<UserData> getById(long id);

    Optional<List<UserData>> getAll();

    UserData update(UserData userData);

    void updatePassword(String newPassword, String newSalt, long id);

    Optional<UserData> getUserByEmail(String email);

    Optional<List<UserData>> getByRole(String roleName);

    void delete(UserData userData);

    void delete(long id);

    UserData save(UserData userData);

    UserData findOrSave(UserData applicant);
}
