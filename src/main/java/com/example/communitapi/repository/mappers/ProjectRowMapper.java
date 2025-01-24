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

    public static Project mapRow(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setId(ResultSetHelper.getLongValue(rs, "id_project"));
        project.setName(ResultSetHelper.getStringValue(rs, "name_project"));
        project.setDescription(ResultSetHelper.getStringValue(rs, "description_project"));
        project.setLocation(ResultSetHelper.getStringValue(rs, "location_project"));

        UserData applicant = new UserData();
        applicant.setSurname(ResultSetHelper.getStringValue(rs, "surname_client_data"));
        applicant.setFirstName(ResultSetHelper.getStringValue(rs,"first_name_client_data"));
        applicant.setPatronymic(ResultSetHelper.getStringValue(rs, "patronymic_client_data"));

        Client client = new Client();
        client.setApplicant(applicant);
        client.setId(ResultSetHelper.getLongValue(rs, "id_client"));
        project.setClient(client);

        Company company = new Company();
        company.setId(ResultSetHelper.getLongValue(rs, "id_company"));
        company.setName(ResultSetHelper.getStringValue(rs, "name_company"));
        project.setCompany(company);

        return project;
    }

    public static List<Project> mapRows(ResultSet rs) throws SQLException {
        List<Project> projects = new ArrayList<>();
        rs.beforeFirst();
        while(rs.next()) {
            Project project = mapRow(rs);
            projects.add(project);
        }
        return projects;
    }
}
