package com.example.communitapi.web.dto.project;

import com.example.communitapi.entities.client.Client;
import com.example.communitapi.entities.company.Company;
import com.example.communitapi.entities.worker.Worker;
import com.example.communitapi.web.dto.validation.OnCreate;
import com.example.communitapi.web.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Project DTO")
public class ProjectDto {

    @Schema(description = "Project id", example = "1")
    @NotNull(message = "ID must be not null", groups = OnUpdate.class)
    private long id;

    @Schema(description = "Project name", example = "ООО Пример")
    @NotNull(message = "Name must be not null.",
            groups = {OnUpdate.class, OnCreate.class})
    private String name;

    @Schema(description = "Project description")
    private String description;

    @Schema(description = "Project location", example = "ул. Пушника, д. 10")
    @NotNull(message = "Location must be not null.",
            groups = {OnUpdate.class, OnCreate.class})
    private String location;

    @Schema(description = "Company of project")
    @NotNull(message = "Company must be not null.",
            groups = {OnUpdate.class, OnCreate.class})
    private Company company;

    @NotNull(message = "Client must be not null.",
            groups = {OnUpdate.class, OnCreate.class})
    private Client client;
    private List<Worker> workers;
}
