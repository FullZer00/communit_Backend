package com.example.communitapi.repository.impl;

import com.example.communitapi.entities.client.Client;
import com.example.communitapi.entities.company.Company;
import com.example.communitapi.entities.exceptions.ResourceMappingException;
import com.example.communitapi.entities.userData.UserData;
import com.example.communitapi.repository.*;
import com.example.communitapi.repository.mappers.ClientRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

//@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {

    private final DataSourceConfig dataSourceConfig;

    private final UserDataRepository userDataRepository;
    private final CompanyRepository companyRepository;

    protected final String FIND_BY_ID_CLIENT = """
            SELECT c.id as id,
                               ud.*,
                               com.name as company_name
                        FROM communit.clients c
                                 inner join communit.user_data_view ud on c.user_data_id = ud.user_data_id
                                 inner join communit.companies com on c.company_id = com.id
                        WHERE c.id = ?;
            """;

    protected final String FIND_BY_FULLNAME = """
            
            """;

    protected final String FIND_BY_EMAIL_CLIENT = """
            SELECT c.id as id,
                    ud.*,
                    com.name as company_name
             FROM communit.clients c
                      inner join communit.user_data_view ud on c.user_data_id = ud.user_data_id
                      inner join communit.companies com on c.company_id = com.id
             WHERE ud.email = ?;
            """;

    private final String FIND_ALL_CLIENTS = """
            SELECT c.* from communit.clients c
            """;

    private final String DELETE_CLIENTS = """
            DELETE FROM communit.clients c
            WHERE c.id = ?;
            """;

    private final String UPDATE_CLIENT = """
            UPDATE communit.clients c
            SET user_data_id = ?,
                company_id = ?
            WHERE C.id = ?;
            """;

    private final String CREATE_CLIENT = """
            INSERT INTO communit.clients (user_data_id,company_id)
            values (?, ?);
            """;

    private final String CHECK_PROJECT_OWNER = """
            SELECT EXISTS (
                SELECT 1
                FROM communit.projects
                WHERE id = ? AND client_id = ?
            ) AS is_project_owned_by_user;
            """;

    @Override
    public Optional<Client> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_CLIENT);
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(ClientRowMapper.mapRow(rs));
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Error while finding client by id.");
        }
    }

    @Override
    public Optional<List<Client>> findAll() {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_CLIENTS);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.of(ClientRowMapper.mapRows(rs));
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Error while finding all clients.");
        }
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL_CLIENT);
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(ClientRowMapper.mapRow(rs));
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Error white finding client by email.");
        }
    }

    @Override
    public Optional<List<Client>> findAllByFullName(String fullName) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_FULLNAME);
            statement.setString(1, fullName);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.of(ClientRowMapper.mapRows(rs));
            }
        } catch (SQLException ex) {
            throw new ResourceMappingException("Error white finding client by full name.");
        }
    }

    @Override
    public boolean checkProjectOwner(long projectId, long clientId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CHECK_PROJECT_OWNER);
            statement.setLong(1, projectId);
            statement.setLong(2, clientId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                return rs.getBoolean(1);
            }
        }
        catch (SQLException ex) {
            throw new ResourceMappingException(ex.getMessage());
        }
    }

    @Override
    public void save(Client client) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            UserData userData = userDataRepository.findOrSave(client.getApplicant());
            Company company = companyRepository.findOrSave(client.getCompany());
            PreparedStatement statement = connection.prepareStatement(CREATE_CLIENT, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, userData.getId());
            statement.setLong(2, company.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Error while creating client.");
        }
    }

    @Override
    public void update(Client client) {
        String updatingError = "Error while updating client.";
        try {
            Connection connection = dataSourceConfig.getConnection();
            userDataRepository.update(client.getApplicant());
            PreparedStatement statement = connection.prepareStatement(UPDATE_CLIENT);
            statement.setLong(1, client.getUserDataId());
            statement.setLong(2, client.getCompanyId());
            statement.setLong(3, client.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new ResourceMappingException(updatingError);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_CLIENTS);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            rs.close();
        } catch (SQLException ex) {
            throw new ResourceMappingException("Error while deleting client.");
        }
    }
}
