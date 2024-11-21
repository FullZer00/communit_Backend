package com.example.communitapi.repository;

import com.example.communitapi.entities.role.Role;
import com.example.communitapi.entities.userData.UserData;

import java.util.List;
import java.util.Optional;

public interface UserDataRepository {

    Optional<UserData> getById(long id);

    Optional<List<UserData>> getAll();

    Optional<UserData> update(UserData userData);

    Optional<UserData> getUserByEmail(String email);

    Optional<List<UserData>> getByRole(Role role);

    void delete(UserData userData);

    void delete(long id);

    Optional<UserData> save(UserData userData);
}
