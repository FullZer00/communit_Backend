package com.example.communitapi.repository.mappers;

import com.example.communitapi.entities.client.Client;
import com.example.communitapi.entities.company.Company;
import com.example.communitapi.entities.project.Project;
import com.example.communitapi.entities.userData.UserData;
import com.example.communitapi.repository.helpers.ResultSetHelper;
import lombok.RequiredArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ProjectRowMapper {

    static ResultSetHelper rsHelper;

    public static Project mapRow(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setId(rsHelper.getLongValue(rs, "id_project"));
        project.setName(rsHelper.getStringValue(rs, "name_project"));
        project.setDescription(rsHelper.getStringValue(rs, "description_project"));
        project.setLocation(rsHelper.getStringValue(rs, "location_project"));

        UserData applicant = new UserData();
        applicant.setSurname(rsHelper.getStringValue(rs, "surname_client_data"));
        applicant.setFirstName(rsHelper.getStringValue(rs,"first_name_client_data"));
        applicant.setPatronymic(rsHelper.getStringValue(rs, "patronymic_client_data"));

        Client client = new Client();
        client.setApplicant(applicant);
        client.setId(rsHelper.getLongValue(rs, "id_client"));
        project.setClient(client);

        Company company = new Company();
        company.setId(rsHelper.getLongValue(rs, "id_company"));
        company.setName(rsHelper.getStringValue(rs, "name_company"));
        project.setCompany(company);

        return project;
    }

    public static List<Project> mapRows(ResultSet rs) throws SQLException {
        List<Project> projects = new ArrayList<>();
        while(rs.next()) {
            Project project = mapRow(rs);
            projects.add(project);
        }
        return projects;
    }
}
