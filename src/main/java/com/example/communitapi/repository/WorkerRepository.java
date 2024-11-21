package com.example.communitapi.repository;

import com.example.communitapi.entities.role.Role;
import com.example.communitapi.entities.worker.Worker;

import java.util.List;
import java.util.Optional;

public interface WorkerRepository {

    Optional<Worker> findById(long id);

    Optional<List<Worker>> findByFullName(String fullName);

    Optional<List<Worker>> findAll();

    Optional<Worker> findByEmail(String email);

    Optional<List<Worker>> findAllByProjectId(long projectId);

    void save(Worker worker);

    void update(Worker worker);

    void insertWorkerRole(long userId, Role role);

    void delete(long id);
}
