package com.example.communitapi.service.Impl;

import com.example.communitapi.entities.exceptions.ResourceNotFoundException;
import com.example.communitapi.entities.userData.UserData;
import com.example.communitapi.repository.RoleRepository;
import com.example.communitapi.repository.UserDataRepository;
import com.example.communitapi.service.PasswordService;
import com.example.communitapi.service.UserDataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class UserDataServiceImpl implements UserDataService {

    private final PasswordService passwordService;
    private final UserDataRepository userDataRepository;
    private final RoleRepository roleRepository;
    private TransactionTemplate transactionTemplate;

    @Override
    @Transactional(readOnly = true)
    public UserData getById(long id) {
        return userDataRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserData getUserByEmail(String email) {
        UserData userData = userDataRepository.findUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//        userData.setRoles(roleRepository.findByUserDataId(userData.getId())
//            .orElseThrow(() -> null));

        return userData;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserData> getAllUsers() {
        return userDataRepository.findAll()
                .orElseThrow(() -> new ResourceNotFoundException("Users not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserData> getUsersByRole(String roleName) {
        return userDataRepository.findByRole(roleName)
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

    @Override
    @Transactional
    public UserData authenticate(String email, String password) {
        UserData userData = getUserByEmail(email);

        if (passwordService.checkPassword(password, userData.getPassword())) {
            return userData;
        }

        return null;
    }
}
