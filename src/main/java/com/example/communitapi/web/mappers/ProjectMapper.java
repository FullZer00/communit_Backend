package com.example.communitapi.web.mappers;

import com.example.communitapi.entities.project.Project;
import com.example.communitapi.web.dto.project.ProjectDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    ProjectDto toDto (Project project);

    List<ProjectDto> toDto (List<Project> project);

    Project toEntity (ProjectDto projectDto);
}
