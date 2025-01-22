package com.example.communitapi.service.Impl;

import com.example.communitapi.entities.exceptions.ResourceNotFoundException;
import com.example.communitapi.entities.role.Role;
import com.example.communitapi.entities.worker.Worker;
import com.example.communitapi.repository.WorkerRepository;
import com.example.communitapi.service.WorkerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;

    @Override
    @Transactional(readOnly = true)
    public Worker getById(long id) {
        return workerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found worker"));
    }

    @Override
    @Transactional(readOnly = true)
    public Worker getByEmail(String email) {
        return workerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Not found worker"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Worker> getByFullName(String fullName) {
        return workerRepository.findByFullName(fullName)
                .orElseThrow(() -> new ResourceNotFoundException("Not found worker"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Worker> getAll() {
        return workerRepository.findAll()
                .orElseThrow(() -> new ResourceNotFoundException("Not found workers"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Worker> getAllByProjectId(long projectId) {
        return workerRepository.findAllByProjectId(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found workers"));
    }

    @Override
    public Worker create(Worker worker) {
        workerRepository.save(worker);
        return worker;
    }

    @Override
    public Worker update(long id, Worker worker) {
        workerRepository.update(worker);
        return worker;
    }

    @Override
    public Worker insertWorkerRole(long userId, long roleId) {
        return null;
    }

    @Override
    public Worker insertWorkerRole(long workerId, Role role) {
        workerRepository.insertWorkerRole(workerId, role.getId());
        return workerRepository.findById(workerId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found worker"));
    }

    @Override
    public void deleteById(long id) {
        workerRepository.delete(id);
    }
}
