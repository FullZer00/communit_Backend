package com.example.communitapi.service;

import com.example.communitapi.entities.project.Project;

import java.util.List;

public interface ProjectService {

    Project getById(long id);

    List<Project> getAll(String fieldName, Object value) throws NoSuchMethodException;

    Project create(Project project);

    Project update(long id, Project project);

    void delete(long id);

}
