package com.example.communitapi.service;

public interface PasswordService {

    String hashPassword(String password);

    boolean checkPassword(String rawPassword, String hashedPassword);
}
