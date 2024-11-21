package com.example.communitapi.entities.userData;

import com.example.communitapi.entities.role.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserData {
    private long id;
    private String firstName;
    private String patronymic;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private List<Role> roles;

    public String getFullName() {
        return firstName + " " + patronymic + " " + surname;
    }
}
