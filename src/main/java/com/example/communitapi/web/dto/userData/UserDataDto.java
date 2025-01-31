package com.example.communitapi.web.dto.userData;

import com.example.communitapi.web.dto.validation.OnCreate;
import com.example.communitapi.web.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Schema(description = "User data DTO")
public class UserDataDto {
    @Schema(description = "User data id", example = "1")
    @NotNull(message = "ID must be not null", groups = OnUpdate.class)
    private long id;

    @Schema(description = "User first name", example = "Иван")
    @NotEmpty(message = "First name must be not empty", groups = {OnCreate.class, OnUpdate.class})
    private String firstName;

    @Schema(description = "User patronymic", example = "Иванович")
    private String patronymic;

    @Schema(description = "User surname", example = "Иванов")
    @NotBlank(message = "Surname must be not null", groups = {OnCreate.class, OnUpdate.class})
    private String surname;

    @Schema(description = "User email", example = "test@gmail.com")
    @NotBlank(message = "Email must be not null", groups = {OnCreate.class, OnUpdate.class})
    private String email;

    @Schema(description = "User phone", example = "89999999999")
    @Length(max = 20, min = 11,
            message = "Number phone must be smaller than 20 symbols and more than 11 symbols",
            groups = {OnCreate.class, OnUpdate.class})
    private String phone;
}
