package com.example.communitapi.web.dto.validation;

import com.example.communitapi.annotaions.PasswordMatches;
import com.example.communitapi.web.dto.userData.UserDataWithPasswordDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        // Приводим объект к нужному типу
        UserDataWithPasswordDto user = (UserDataWithPasswordDto) obj;

        // Проверяем совпадение паролей
        boolean result = user.getPassword().equals(user.getConfirmPassword());

        if(!result) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Пароли не совпадают")
                    .addPropertyNode("confirmPassword") // Указываем поле, к которому относится ошибка
                    .addConstraintViolation();
        }

        return result;
    }
}
