package com.example.communitapi.repository.mappers;

import com.example.communitapi.entities.client.Client;
import com.example.communitapi.entities.company.Company;
import com.example.communitapi.entities.userData.UserData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientRowMapper {

    public static Client mapRow(ResultSet rs) throws SQLException {
        Client client = new Client();
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

    public static List<Client> mapRows(ResultSet rs) throws SQLException {
        List<Client> clients = new ArrayList<>();
        while (rs.next()) {
            Client client = mapRow(rs);
            clients.add(client);
        }

        return clients;
    }
}
