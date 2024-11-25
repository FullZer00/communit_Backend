package com.example.communitapi.repository.impl;

import com.example.communitapi.entities.role.Role;
import com.example.communitapi.entities.userData.UserData;
import com.example.communitapi.repository.UserDataRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDataRepositoryImpl implements UserDataRepository {


    @Override
    public Optional<UserData> getById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<List<UserData>> getAll() {
        return Optional.empty();
    }

    @Override
    public UserData update(UserData userData) {
        return null;
    }

    @Override
    public Optional<UserData> getUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<List<UserData>> getByRole(Role role) {
        return Optional.empty();
    }

    @Override
    public void delete(UserData userData) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public UserData save(UserData userData) {
        return null;
    }

    @Override
    public UserData findOrSave(UserData applicant) {
        return null;
    }
}
