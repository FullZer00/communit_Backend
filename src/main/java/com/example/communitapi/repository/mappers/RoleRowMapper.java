package com.example.communitapi.repository.mappers;

import com.example.communitapi.entities.role.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleRowMapper {

    public static Role mapRow(ResultSet rs) throws SQLException {
        Role role = new Role();
        role.setId(rs.getLong("id_role"));
        role.setName(rs.getString("name_role"));
        role.setRuName(rs.getString("ru_name_role"));

        return role;
    }

    public static List<Role> mapRows(ResultSet rs) throws SQLException {
        ArrayList<Role> roles = new ArrayList<>();
        while (rs.next()) {
            roles.add(mapRow(rs));
        }

        return roles;
    }
}
