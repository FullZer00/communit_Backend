package com.example.communitapi.repository.impl;

import com.example.communitapi.entities.exceptions.ResourceMappingException;
import com.example.communitapi.entities.project.Project;
import com.example.communitapi.repository.DataSourceConfig;
import com.example.communitapi.repository.ProjectRepository;
import com.example.communitapi.repository.mappers.ProjectRowMapper;
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
public class ProjectRepositoryImpl implements ProjectRepository {

    private static DataSourceConfig dataSourceConfig;

    private final String SELECT_QUERY = """
            select
                pr.id as id_project,
                pr.name as name_project,
                pr.description as description_project,
                cl.id as id_client,
                cl_data.surname as surname_client_data,
                cl_data.first_name as first_name_client_data,
                cl_data.patronymic as patronymic_client_data,
                com.id as id_company,
                com.name as name_company
            from communit.projects pr
                inner join communit.clients cl on pr.client_id = cl.id
                inner join communit.user_data_view cl_data on cl.user_data_id = cl_data.user_data_id
                inner join communit.companies com on pr.company_id = com.id;
            """;

    private final String FIND_ALL = SELECT_QUERY;

    private final String FIND_BY_ID = SELECT_QUERY + " where id_project=?";

    private final String FIND_BY_COMPANY_ID = SELECT_QUERY + " where id_company=?";

    private final String FIND_BY_CLIENT_ID = SELECT_QUERY + " where id_client=?";

    private final String FIND_BY_WORKER_ID = """
            select
                pr.id as id_project,
                pr.name as name_project,
                pr.description as description_project,
                cl.id as id_client,
                cl_data.surname || ' ' || cl_data.first_name || ' ' || COALESCE(cl_data.patronymic, '') as client_fio,
                com.name as name_company
            from communit.projects pr
                inner join communit.clients cl on pr.client_id = cl.id
                inner join communit.user_data_view cl_data on cl.user_data_id = cl_data.user_data_id
                inner join communit.companies com on pr.company_id = com.id
                inner join communit.projects_workers project_workers on pr.id = project_workers.project_id
            where project_workers.worker_id = ?
            """;

    private final String CREATE = """
            insert into communit.projects (name, description, location, company_id, client_id)
            values (?, ?, ?, ?, ?)
            """;

    private final String UPDATE = """
            update communit.projects
                set name = ?,
                    description = ?,
                    location = ?,
                    company_id = ?,
                    client_id = ?
            where id = ?
            """;

    private final String DELETE = """
            delete from communit.projects
            where id = ?;
            """;


    @Override
    public Optional<Project> findById(long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return Optional.of(ProjectRowMapper.mapRow(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException(e);
        }
    }

    @Override
    public Optional<List<Project>> findAllByCompanyId(long companyId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_COMPANY_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setLong(1, companyId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return Optional.of(ProjectRowMapper.mapRows(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException(e);
        }
    }

    @Override
    public Optional<List<Project>> findAllByClientId(long clientId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_CLIENT_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setLong(1, clientId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return Optional.of(ProjectRowMapper.mapRows(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException(e);
        }
    }

    @Override
    public Optional<List<Project>> findAllByWorkerId(long workerId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_WORKER_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setLong(1, workerId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                rs.next();
                return Optional.of(ProjectRowMapper.mapRows(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException(e);
        }
    }

    @Override
    public Project save(Project project) {
        try {
            PreparedStatement statement = getPreparedStatement(project);
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                project.setId(rs.getLong(1));
                return project;
            }
        } catch (SQLException e) {
            throw new ResourceMappingException(e.getMessage());
        }
    }

    private PreparedStatement getPreparedStatement(Project project) throws SQLException {
        Connection connection = dataSourceConfig.getConnection();
        PreparedStatement statement = connection.prepareStatement(CREATE,
                PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, project.getName());
        statement.setString(2, project.getDescription());
        statement.setString(3, project.getLocation());
        statement.setLong(4, project.getCompany().getId());
        statement.setLong(5, project.getClient().getId());
        return statement;
    }

    @Override
    public Project update(long id, Project project) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setString(3, project.getLocation());
            statement.setLong(4, project.getCompany().getId());
            statement.setLong(5, project.getClient().getId());
            statement.executeUpdate();
            project.setId(id);
            return project;
        } catch (SQLException e) {
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException(e.getMessage());
        }
    }
}
