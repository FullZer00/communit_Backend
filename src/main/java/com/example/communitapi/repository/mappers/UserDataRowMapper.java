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

    private static ResultSetHelper rsHelper;

    public static UserData mapRow(ResultSet rs) throws SQLException {
        if (rs.next()) {
            UserData userData = new UserData();
            userData.setId(rsHelper.getLongValue(rs,"id_user_data"));
            userData.setSurname(rsHelper.getStringValue(rs,"surname_user_data"));
            userData.setFirstName(rsHelper.getStringValue(rs,"first_name_user_data"));
            userData.setPatronymic(rsHelper.getStringValue(rs,"patronymic_user_data"));
            userData.setPhone(rsHelper.getStringValue(rs,"phone_user_data"));
            userData.setEmail(rsHelper.getStringValue(rs,"email_user_data"));
            userData.setPassword(rsHelper.getStringValue(rs,"password_user_data"));
            userData.setSaltPassword(rsHelper.getStringValue(rs, "salt_password_user_data"));

            return userData;
        }

        return null;
    }

    public static List<UserData> mapRows(ResultSet rs) throws SQLException {
        List<UserData> userDataList = new ArrayList<>();
        while (rs.next()) {
            UserData userData = mapRow(rs);
            userDataList.add(userData);
        }
        return userDataList;
    }
}
