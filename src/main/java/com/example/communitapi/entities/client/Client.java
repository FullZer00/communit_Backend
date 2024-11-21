package com.example.communitapi.entities.client;

import com.example.communitapi.entities.company.Company;
import com.example.communitapi.entities.userData.UserData;
import lombok.Data;

@Data
public class Client {
    private long id;
    private UserData applicant;
    private Company company;
}