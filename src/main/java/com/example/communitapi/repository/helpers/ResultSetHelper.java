package com.example.communitapi.repository.helpers;

import com.example.communitapi.entities.exceptions.ResourceMappingException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetHelper {

    public String getStringValue(ResultSet resultSet, String nameField) {
        try {
            String value = resultSet.getString(nameField);
            if (value.isEmpty()) return "";
            return value;
        } catch (SQLException e) {
            throw new ResourceMappingException("Поле " + nameField + " не существует\n" + e.getMessage());
        }
    }

    public Long getLongValue(ResultSet resultSet, String nameField) {
        try {
            return resultSet.getLong(nameField);
        } catch (SQLException e) {
            throw new ResourceMappingException("Поле " + nameField + " не существует\n" + e.getMessage());
        }
    }
}
