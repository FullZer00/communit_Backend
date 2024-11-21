package com.example.communitapi.service;

import com.example.communitapi.entities.role.Role;
import com.example.communitapi.entities.worker.Worker;

import java.util.List;

public interface WorkerService {

    Worker getById(long id);

    Worker getByEmail(String email);

    List<Worker> getByFullName(String fullName);

    List<Worker> getAll();

    List<Worker> getAllByProjectId(long projectId);

    Worker create(Worker worker);

    Worker update(long id, Worker worker);

    Worker insertWorkerRole(long userId, long roleId);

    Worker insertWorkerRole(long userId, Role role);

    void deleteById(long id);

}
