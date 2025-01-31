package com.example.communitapi.repository;

import com.example.communitapi.entities.userData.UserData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserDataRepository {

    Optional<UserData> findById(long id);

    Optional<List<UserData>> findAll();

    void update(UserData userData);

    void updatePassword(@Param("newPassword") String newPassword, @Param("id") long id);

    Optional<UserData> findUserByEmail(String email);

    Optional<List<UserData>> findByRole(String roleName);

    void delete(UserData userData);

    void delete(long id);

    void save(UserData userData);

    UserData findOrSave(UserData applicant);
}
