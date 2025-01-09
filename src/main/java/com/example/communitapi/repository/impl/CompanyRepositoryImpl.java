package com.example.communitapi.repository.impl;

import com.example.communitapi.entities.company.Company;
import com.example.communitapi.entities.exceptions.ResourceMappingException;
import com.example.communitapi.repository.CompanyRepository;
import com.example.communitapi.repository.DataSourceConfig;
import com.example.communitapi.repository.mappers.CompanyRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepository {

    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = "SELECT * FROM companies WHERE id = ?";

    private final String FIND_BY_ALL = """
            SELECT * FROM company
            """;

    private final String FIND_BY_NAME = """
                SELECT * FROM communit.companies c WHERE c.name ilike '%' || ? || '%';
            """;

    private final String CREATE_COMPANY = """
            INSERT INTO communit.companies (name, address)
            values (?, ?);
            """;

    private final String UPDATE_COMPANY = """
            update communit.companies
                set name = ?,
                    address = ?
                where id = ?;
            """;

    private final String DELETE_COMPANY = """
            delete from communit.companies
                where id = ?;
            """;

    @Override
    public Optional<Company> findById(long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    FIND_BY_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
                    );
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(CompanyRowMapper.mapRow(rs));
            }
        }
        catch (SQLException e) {
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public Optional<List<Company>> findAll() {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    FIND_BY_ALL,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.of(CompanyRowMapper.mapRows(rs));
            }
        }
        catch (SQLException e) {
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public Optional<List<Company>> findByName(String name) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    FIND_BY_NAME,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            statement.setString(1, name);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.of(CompanyRowMapper.mapRows(rs));
            }
        }
        catch (SQLException e) {
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public Company save(Company company) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE_COMPANY,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, company.getName());
            statement.setString(2, company.getAddress());
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                company.setId(rs.getLong(1));
                return company;
            }
        } catch (SQLException e) {
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public void delete(Company company) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_COMPANY);
            statement.setLong(1, company.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public Company update(long id, Company company) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_COMPANY);
            statement.setString(1, company.getName());
            statement.setString(2, company.getAddress());
            statement.setLong(3, id);
            statement.executeUpdate();
            company.setId(id);
            return company;
        } catch (SQLException e) {
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public Company findOrSave(Company company) {
        if (findById(company.getId()).isPresent()) {
            return company;
        }

        return save(company);
    }
}
