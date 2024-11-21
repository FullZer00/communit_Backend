package com.example.communitapi.web.dto.userData;

import com.example.communitapi.web.dto.validation.OnCreate;
import com.example.communitapi.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class UserDataDto {
    @NotNull(message = "ID must be not null", groups = OnUpdate.class)
    private long id;
    @NotNull(message = "First name must be not null", groups = {OnCreate.class, OnUpdate.class})
    private String firstName;
    private String patronymic;
    @NotNull(message = "Surname must be not null", groups = {OnCreate.class, OnUpdate.class})
    private String surname;
    @NotNull(message = "Email must be not null", groups = {OnCreate.class, OnUpdate.class})
    private String email;
    @Length(max = 20, min = 12,
            message = "Number phone must be smaller than 20 symbols and more than 12 symbols",
            groups = {OnCreate.class, OnUpdate.class})
    private String phone;
}
