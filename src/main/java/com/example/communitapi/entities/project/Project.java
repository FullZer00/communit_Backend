package com.example.communitapi.entities.project;

import com.example.communitapi.entities.client.Client;
import com.example.communitapi.entities.company.Company;
import com.example.communitapi.entities.worker.Worker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
public class Project {
    private long id;
    private String name;
    private String description;
    private String location;

    private Company company;
    private Client client;
    private List<Worker> workers;
}
