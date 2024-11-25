package com.example.communitapi.repository.impl;

import com.example.communitapi.entities.worker.Worker;
import com.example.communitapi.entities.role.Role;
import com.example.communitapi.repository.WorkerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WorkerRepositoryImpl implements WorkerRepository {
    @Override
    public Optional<Worker> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Worker>> findByFullName(String fullName) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Worker>> findAll() {
        return Optional.empty();
    }

    @Override
    public Optional<Worker> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Worker>> findAllByProjectId(long projectId) {
        return Optional.empty();
    }

    @Override
    public void save(Worker worker) {

    }

    @Override
    public void update(Worker worker) {

    }

    @Override
    public void insertWorkerRole(long userId, Role role) {

    }

    @Override
    public void delete(long id) {

    }
}
