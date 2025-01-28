package com.example.communitapi.annotaions;

import com.example.communitapi.web.dto.validation.PasswordMatchesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // Аннотация применяется к классу
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class) // Указываем валидатор
public @interface PasswordMatches {
    String message() default "Пароли не совпадают"; // Сообщение об ошибке

    Class<?>[] groups() default {}; // Группы валидации

    Class<? extends Payload>[] payload() default {}; // Полезная нагрузка
}

