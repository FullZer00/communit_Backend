package com.example.communitapi.web.dto.project;

import com.example.communitapi.entities.client.Client;
import com.example.communitapi.entities.company.Company;
import com.example.communitapi.entities.worker.Worker;
import com.example.communitapi.web.dto.validation.OnCreate;
import com.example.communitapi.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ProjectDto {

    @NotNull(message = "ID must be not null", groups = OnUpdate.class)
    private long id;

    @NotNull(message = "Name must be not null.",
            groups = {OnUpdate.class, OnCreate.class})
    private String name;

    private String description;

    @NotNull(message = "Location must be not null.",
            groups = {OnUpdate.class, OnCreate.class})
    private String location;

    @NotNull(message = "Company must be not null.",
            groups = {OnUpdate.class, OnCreate.class})
    private Company company;

    @NotNull(message = "Client must be not null.",
            groups = {OnUpdate.class, OnCreate.class})
    private Client client;
    private List<Worker> workers;
}
