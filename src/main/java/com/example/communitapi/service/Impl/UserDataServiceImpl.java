package com.example.communitapi.service.Impl;

import com.example.communitapi.entities.exceptions.ResourceNotFoundException;
import com.example.communitapi.entities.role.Role;
import com.example.communitapi.entities.userData.UserData;
import com.example.communitapi.repository.UserDataRepository;
import com.example.communitapi.service.UserDataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserDataServiceImpl implements UserDataService {

    private final UserDataRepository userDataRepository;

    @Override
    @Transactional(readOnly = true)
    public UserData getById(long id) {
        return userDataRepository.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserData getUserByEmail(String email) {
        return userDataRepository.getUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserData> getAllUsers() {
        return userDataRepository.getAll()
                .orElseThrow(() -> new ResourceNotFoundException("Users not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserData> getUsersByRole(Role role) {
        return userDataRepository.getByRole(role)
                .orElseThrow(() -> new ResourceNotFoundException("Users not found"));
    }

    @Override
    @Transactional
    public UserData createUser(UserData userData) {
        userDataRepository.save(userData);
        return userData;
    }

    @Override
    public UserData updateUser(UserData userData) {
        userDataRepository.update(userData);
        return userData;
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userDataRepository.delete(id);
    }

    @Override
    @Transactional
    public void deleteUser(UserData userData) {
        userDataRepository.delete(userData);
    }
}
