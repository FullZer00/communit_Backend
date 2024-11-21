package com.example.communitapi.web.controllers;

import com.example.communitapi.entities.project.Project;
import com.example.communitapi.entities.worker.Worker;
import com.example.communitapi.service.ProjectService;
import com.example.communitapi.service.WorkerService;
import com.example.communitapi.web.dto.project.ProjectDto;
import com.example.communitapi.web.dto.worker.WorkerDto;
import com.example.communitapi.web.mappers.ProjectMapper;
import com.example.communitapi.web.mappers.WorkerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.path}/projects")
@Validated
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final WorkerService workerService;

    private final ProjectMapper projectMapper;
    private final WorkerMapper workerMapper;

    @GetMapping("/{id}")
    public ProjectDto getById(@PathVariable long id){
        return projectMapper.toDto(projectService.getById(id));
    }

    @GetMapping
    public List<ProjectDto> getAll(
            @RequestParam(name="field", required = false, defaultValue = "") String field,
            @RequestParam(name="value", defaultValue = "") String value
            ) throws NoSuchMethodException {
        List<Project> projects = projectService.getAll(field, value);

        return projectMapper.toDto(projects);
    }

    @GetMapping("/{id}/workers")
    public List<WorkerDto> getWorkersByProjectId(@PathVariable long id){
        List<Worker> workers = workerService.getAllByProjectId(id);

        return workerMapper.toDto(workers);
    }

    @PostMapping
    public ProjectDto create(@RequestBody ProjectDto projectDto){
        Project project = projectMapper.toEntity(projectDto);

        Project newProject = projectService.create(project);

        return projectMapper.toDto(newProject);
    }


    @PutMapping("/{id}")
    public ProjectDto update(@PathVariable long id, @RequestBody ProjectDto projectDto){
        Project project = projectMapper.toEntity(projectDto);
        Project updatedProject = projectService.update(id, project);

        return projectMapper.toDto(updatedProject);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        projectService.delete(id);
    }

}
