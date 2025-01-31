package com.example.communitapi.repository;

import com.example.communitapi.entities.worker.Worker;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface WorkerRepository {

    Optional<Worker> findById(long id);

    Optional<List<Worker>> findByFullName(String fullName);

    Optional<List<Worker>> findAll();

    Optional<Worker> findByEmail(String email);

    Optional<List<Worker>> findAllByProjectId(long projectId);

    Worker save(Worker worker);

    void update(Worker worker);

    void insertWorkerRole(long userId, long roleId);

    void delete(long id);
}
