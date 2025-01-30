package com.example.communitapi.repository;

import com.example.communitapi.entities.project.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    Optional<Project> findById(long id);

    Optional<List<Project>> findAll();

    Optional<List<Project>> findAllByCompanyId(long companyId);

    Optional<List<Project>> findAllByClientId(long clientId);

    Optional<List<Project>> findAllByWorkerId(long personId);

    Project save(Project project);

    Project update(long id, Project project);

    void delete(long id);
}
