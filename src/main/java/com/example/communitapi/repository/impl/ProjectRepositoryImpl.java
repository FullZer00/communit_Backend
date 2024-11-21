package com.example.communitapi.repository.impl;

import com.example.communitapi.entities.project.Project;
import com.example.communitapi.repository.ProjectRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {
    @Override
    public Optional<Project> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Project>> findAllByCompanyId(long companyId) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Project>> findAllByClientId(long clientId) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Project>> findAllByPersonId(long personId) {
        return Optional.empty();
    }

    @Override
    public void save(Project project) {

    }

    @Override
    public void update(long id, Project project) {

    }

    @Override
    public void delete(long id) {

    }
}
