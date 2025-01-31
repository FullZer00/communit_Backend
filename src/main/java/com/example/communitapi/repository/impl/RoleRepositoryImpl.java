package com.example.communitapi.repository.impl;

import com.example.communitapi.entities.exceptions.ResourceMappingException;
import com.example.communitapi.entities.role.Role;
import com.example.communitapi.repository.DataSourceConfig;
import com.example.communitapi.repository.RoleRepository;
import com.example.communitapi.repository.mappers.RoleRowMapper;
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
public class RoleRepositoryImpl implements RoleRepository {

    private final DataSourceConfig dataSourceConfig;

    private final String SELECT_QUERY = """
            select r.id as id_role,
                   r.name as name_role,
                   r.ru_name as ru_name_role
            from roles r
            """;

    private final String FIND_BY_ID = SELECT_QUERY + """
             where id = ?
            """;

    private final String FIND_BY_NAME = """
            with input as (select ? as name)
            select r.id as id_role,
                   r.name as name_role,
                   r.ru_name as ru_name_role
            from roles r,
            input
            where r.name = input.name or r.ru_name = input.name
            """;

    private final String FIND_ALL = SELECT_QUERY;

    private final String CHECK_ROLE_NAME = """
            WITH role_name AS (SELECT ? AS value)
            select exists(select * from roles, role_name where roles.name = role_name.value or roles.ru_name = role_name.value);
            """;

    private final String CREATE = """
            insert into roles(name, ru_name)
                values(?, ?);
            """;

    private final String UPDATE = """
            update roles
                set name = ?,
                    ru_name = ?
            where id = ?
            """;

    private final String DELETE = """
            delete from roles where id = ?
            """;

    private final String FIND_BY_USER_DATA_ID = SELECT_QUERY + """
            inner join users_roles ur on ur.role_id = r.id
            where ur.user_id = ?;
            """;

    @Override
    public Optional<Role> findById(long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(RoleRowMapper.mapRow(rs));
            }
        }
        catch (SQLException e) {
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public Optional<Role> findByName(String name) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME);
            preparedStatement.setString(1, name);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(RoleRowMapper.mapRow(rs));
            }
        }
        catch (SQLException e) {
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public Optional<List<Role>> findAll() {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                return Optional.of(RoleRowMapper.mapRows(rs));
            }
        }
        catch (SQLException e) {
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public Optional<List<Role>> findByUserDataId(long userDataId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USER_DATA_ID);

            preparedStatement.setLong(1, userDataId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                return Optional.of(RoleRowMapper.mapRows(rs));
            }
        }
        catch (SQLException e) {
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public Role save(Role role) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, role.getName());
            statement.setString(2, role.getRuName());
            
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                role.setId(rs.getLong(1));
                return role;
            }
            
        }
        catch (SQLException e) {
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
        }
        catch (SQLException e) {
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public void update(Role role) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, role.getName());
            statement.setString(2, role.getRuName());
            statement.setLong(3, role.getId());

            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public boolean checkRole(String roleName) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CHECK_ROLE_NAME);
            statement.setString(1, roleName);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                return rs.getBoolean(1);
            }

        }
        catch (SQLException e) {
            throw new ResourceMappingException(e.getMessage());
        }
    }
}
