package com.example.communitapi.repository.mappers;

import com.example.communitapi.entities.company.Company;
import com.example.communitapi.entities.userData.UserData;
import com.example.communitapi.web.dto.client.ClientDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientRowMapper {

    public static ClientDto mapRow(ResultSet rs) throws SQLException {
        if (rs.next()) {
            ClientDto client = new ClientDto();
            client.setId(rs.getLong("id"));

            Company company = new Company();
            company.setId(rs.getLong("company.id"));
            company.setName(rs.getString("company.name"));
            client.setCompany(company);

            UserData userData = new UserData();
            userData.setId(rs.getLong("ud.id"));
            userData.setSurname(rs.getString("ud.surname"));
            userData.setFirstName(rs.getString("ud.first_name"));
            userData.setPatronymic(rs.getString("ud.patronymic"));
            userData.setEmail(rs.getString("ud.email"));
            userData.setPhone(rs.getString("ud.phone"));
            client.setApplicant(userData);

            return client;
        }
        return null;
    }

    public static List<ClientDto> mapRows(ResultSet rs) throws SQLException {
        List<ClientDto> clients = new ArrayList<>();
        while (rs.next()) {
            ClientDto client = mapRow(rs);
            clients.add(client);
        }

        return clients;
    }
}
