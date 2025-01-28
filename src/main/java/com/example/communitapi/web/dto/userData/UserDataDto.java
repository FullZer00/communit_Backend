package com.example.communitapi.web.dto.userData;

import com.example.communitapi.web.dto.validation.OnCreate;
import com.example.communitapi.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserDataDto {
    @NotNull(message = "ID must be not null", groups = OnUpdate.class)
    private long id;
    @NotEmpty(message = "First name must be not empty", groups = {OnCreate.class, OnUpdate.class})
    private String firstName;
    private String patronymic;
    @NotBlank(message = "Surname must be not null", groups = {OnCreate.class, OnUpdate.class})
    private String surname;
    @NotBlank(message = "Email must be not null", groups = {OnCreate.class, OnUpdate.class})
    private String email;
    @Length(max = 20, min = 11,
            message = "Number phone must be smaller than 20 symbols and more than 11 symbols",
            groups = {OnCreate.class, OnUpdate.class})
    private String phone;
}
