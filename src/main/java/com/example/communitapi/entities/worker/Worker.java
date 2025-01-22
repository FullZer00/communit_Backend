package com.example.communitapi.entities.worker;

import com.example.communitapi.entities.role.Role;
import com.example.communitapi.entities.userData.UserData;
import lombok.Data;

import java.util.List;

@Data
public class Worker {
    private long id;
    private long userDataId;
    private UserData userData;
    private String passportSeria;
    private String passportNumber;
    private double rate;
}
