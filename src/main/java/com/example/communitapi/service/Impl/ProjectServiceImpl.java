package com.example.communitapi.service.Impl;

import com.example.communitapi.entities.exceptions.ResourceNotFoundException;
import com.example.communitapi.entities.project.Project;
import com.example.communitapi.repository.ProjectRepository;
import com.example.communitapi.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static com.example.communitapi.helpers.StringHelpers.capitalize;

@Service
@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    @Transactional(readOnly = true)
    public Project getById(long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getAll(String fieldName, Object value) throws NoSuchMethodException {
        Method method = projectRepository.getClass().getMethod("findBy" + capitalize(fieldName));
        try {
            return (List<Project>) method.invoke(projectRepository, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new ResourceNotFoundException("The Project entity does not have a name " + fieldName);
        }
    }

    @Override
    @Transactional
    public Project create(Project project) {
        projectRepository.save(project);
        return project;
    }

    @Override
    @Transactional
    public Project update(long id, Project project) {
        projectRepository.update(id, project);
        return project;
    }

    @Override
    @Transactional
    public void delete(long id) {
        projectRepository.delete(id);
    }
}
