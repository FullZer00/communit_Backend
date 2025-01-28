package com.example.communitapi.web.dto.userData;

import com.example.communitapi.annotaions.PasswordMatches;
import com.example.communitapi.web.dto.validation.OnCreate;
import com.example.communitapi.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

@EqualsAndHashCode(callSuper = true)
@Data
@PasswordMatches(message = "Пароли не совпадают", groups = {OnCreate.class, OnUpdate.class})
public class UserDataWithPasswordDto extends UserDataDto {
    @NotNull(message="Password must be not null", groups={OnCreate.class, OnUpdate.class})
    @Length(min = 8, max = 80, message = "Password must be more than 6 and smaller than 20")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
    message = "Пароль должен содержать хотябы одну прописную латинскую букву, одну заглавную, одну цифру и один спец. символ.")
    private String password;

    @NotNull
    private String confirmPassword;
}
