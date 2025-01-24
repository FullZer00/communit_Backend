package com.example.communitapi.repository.mappers;

import com.example.communitapi.entities.userData.UserData;
import com.example.communitapi.repository.helpers.ResultSetHelper;
import lombok.AllArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class UserDataRowMapper {
    public static UserData mapRow(ResultSet rs) throws SQLException {
        UserData userData = new UserData();
        userData.setId(ResultSetHelper.getLongValue(rs, "id_user_data"));
        userData.setSurname(ResultSetHelper.getStringValue(rs, "surname_user_data"));
        userData.setFirstName(ResultSetHelper.getStringValue(rs, "first_name_user_data"));
        userData.setPatronymic(ResultSetHelper.getStringValue(rs, "patronymic_user_data"));
        userData.setPhone(ResultSetHelper.getStringValue(rs, "phone_user_data"));
        userData.setEmail(ResultSetHelper.getStringValue(rs, "email_user_data"));
        userData.setPassword(ResultSetHelper.getStringValue(rs, "password_user_data"));
        userData.setSaltPassword(ResultSetHelper.getStringValue(rs, "salt_password_user_data"));

        return userData;
    }

    public static List<UserData> mapRows(ResultSet rs) throws SQLException {
        List<UserData> userDataList = new ArrayList<>();
        rs.beforeFirst();
        while (rs.next()) {
            UserData userData = mapRow(rs);
            userDataList.add(userData);
        }
        return userDataList;
    }
}
