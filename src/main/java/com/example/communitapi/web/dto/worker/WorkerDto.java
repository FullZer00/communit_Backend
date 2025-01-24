package com.example.communitapi.web.dto.worker;

import com.example.communitapi.entities.role.Role;
import com.example.communitapi.entities.userData.UserData;
import com.example.communitapi.web.dto.validation.OnCreate;
import com.example.communitapi.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.List;

@Data
public class WorkerDto {
    @NotNull(message = "ID must be not null", groups = OnUpdate.class)
    private long id;

    @NotNull(message = "Applicant must be not null.",
            groups = {OnUpdate.class, OnCreate.class})
    private UserData userData;

    private long userDataId;

    @NotNull(message = "Passport seria must be not null.",
            groups = {OnUpdate.class, OnCreate.class})
    private String passportSeria;

    @NotNull(message = "Passport number must be not null.",
            groups = {OnUpdate.class, OnCreate.class})
    private String passportNumber;

    @PositiveOrZero(message = "Rate must be not negative")
    private double rate = 0.0;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must be not null.",
            groups = {OnUpdate.class, OnCreate.class})
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password confirmation must be not null.",
            groups = {OnUpdate.class, OnCreate.class})
    private String passwordConfirm;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Role> roles;
}
