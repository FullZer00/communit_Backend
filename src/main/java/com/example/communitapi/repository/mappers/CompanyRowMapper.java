package com.example.communitapi.repository.mappers;

import com.example.communitapi.entities.company.Company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyRowMapper {

    public static Company mapRow(ResultSet rs) throws SQLException {
        Company company = new Company();
        company.setId(rs.getLong("id"));
        company.setName(rs.getString("name"));
        company.setAddress(rs.getString("address"));
        return company;

    }

    public static List<Company> mapRows(ResultSet rs) throws SQLException {
        List<Company> companyList = new ArrayList<>();
        rs.beforeFirst();
        while (rs.next()) {
            Company company = mapRow(rs);
            companyList.add(company);
        }
        return companyList;
    }
}
