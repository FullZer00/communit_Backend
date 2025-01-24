package com.example.communitapi.repository;

import com.example.communitapi.entities.role.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {

    Optional<Role> findById(long id);

    Optional<Role> findByName(String name);

    Optional<List<Role>> findAll();

    Optional<List<Role>>findByUserDataId(long userDataId);

    Role save(Role role);

    void delete(long id);

    void update(Role role);

    boolean checkRole(String roleName);
}
