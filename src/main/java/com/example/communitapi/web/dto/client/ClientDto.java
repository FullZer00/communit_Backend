package com.example.communitapi.web.dto.client;

import com.example.communitapi.entities.company.Company;
import com.example.communitapi.entities.userData.UserData;
import com.example.communitapi.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientDto {

    @NotNull(message = "ID must be not null", groups = OnUpdate.class)
    private long id;
    @NotNull(message = "Applicant must be not null.")
    private UserData applicant;
    @NotNull(message = "Company must be not null.")
    private Company company;

}
