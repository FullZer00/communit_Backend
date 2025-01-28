package com.example.communitapi.repository;

import com.example.communitapi.entities.role.Role;
import com.example.communitapi.entities.userData.UserData;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface UserDataRepository {

    Optional<UserData> findById(long id);

    Optional<List<UserData>> findAll();

    UserData update(UserData userData);

    void updatePassword(@Param("newPassword") String newPassword, @Param("id") long id);

    Optional<UserData> findUserByEmail(String email);

    Optional<List<UserData>> findByRole(String roleName);

    void delete(UserData userData);

    void delete(long id);

    UserData save(UserData userData);

    UserData findOrSave(UserData applicant);
}
