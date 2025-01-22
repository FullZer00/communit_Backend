package com.example.communitapi.repository.impl;

import com.example.communitapi.entities.exceptions.ResourceMappingException;
import com.example.communitapi.entities.userData.UserData;
import com.example.communitapi.repository.DataSourceConfig;
import com.example.communitapi.repository.UserDataRepository;
import com.example.communitapi.repository.mappers.UserDataRowMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserDataRepositoryImpl implements UserDataRepository {

    private final DataSourceConfig dataSourceConfig;

    private final String SELECT_QUERY = """
              select
                ud.id as id_user_data,
                ud.first_name as first_name_user_data,
                ud.surname as surname_user_data,
                ud.patronymic as patronymic_user_data,
                ud.email as email_user_data,
                ud.phone as phone_user_data,
                ud.password as password_user_data,
                ud.salt_password as salt_password_user_data
            from communit.user_data ud
            """;

    private final String FIND_BY_ID = SELECT_QUERY +
            "WHERE ud.id = ?";

    private final String UPDATE = """
            update communit.user_data
                set surname = ?,
                    first_name = ?,
                    patronymic = ?,
                    email = ?,
                    phone = ?
            where id = ?;
            """;

    private final String UPDATE_PASSWORD = """
        update communit.user_data
            set password = ?,
            salt_password = ?
        where id = ?
    """;

    private final String FIND_ALL = SELECT_QUERY;

    private final String FIND_BY_EMAIL = SELECT_QUERY +
            "WHERE email = ?";

    private final String FIND_BY_ROLE = """
                WITH role_name AS (SELECT ? AS user_input)
                select
                    ud.id as id_user_data,
                    ud.first_name as first_name_user_data,
                    ud.surname as surname_user_data,
                    ud.patronymic as patronymic_user_data,
                    ud.email as email_user_data,
                    ud.phone as phone_user_data
                from communit.user_data ud
                left join communit.users_roles ur on ur.user_id = ud.id
                inner join communit.roles r on r.id = ur.role_id,
                role_name
                WHERE r.ru_name = role_name.user_input or r.name = role_name.user_input;
            """;

    private final String DELETE = """
            DELETE from user_data where id = ?
            """;

    private final String CREATE = """
            insert into communit.user_data (surname, first_name, patronymic, email, phone, password, salt_password)
            values (?, ?, ?, ?, ?, ?, ?);
            """;

    @Override
    public Optional<UserData> getById(long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setLong(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return Optional.ofNullable(UserDataRowMapper.mapRow(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException(e);
        }
    }

    @Override
    public Optional<List<UserData>> getAll() {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return Optional.of(UserDataRowMapper.mapRows(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException(e);
        }
    }

    @Override
    public UserData update(UserData userData) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, userData.getSurname());
            statement.setString(2, userData.getFirstName());
            statement.setString(3, userData.getPatronymic());
            statement.setString(4, userData.getEmail());
            statement.setString(5, userData.getPhone());
            statement.setLong(6, userData.getId());
            statement.executeUpdate();
            return userData;

        } catch (SQLException e) {
            throw new ResourceMappingException(e);
        }
    }

    @Override
    public void updatePassword(String newPassword, String newSalt, long idUserData) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, newSalt);
            preparedStatement.setLong(3, idUserData);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException(e);
        }
    }

    @Override
    public Optional<UserData> getUserByEmail(String email) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_EMAIL,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, email);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return Optional.ofNullable(UserDataRowMapper.mapRow(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while user data searching by email");
        }
    }

    @Override
    public Optional<List<UserData>> getByRole(String nameRole) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ROLE,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, nameRole);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                return Optional.of(UserDataRowMapper.mapRows(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException(e);
        }
    }

    @Override
    public void delete(UserData userData) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, userData.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException(e);
        }
    }

    @Override
    public void delete(long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException(e);
        }
    }

    @Override
    public UserData save(UserData userData) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE);
            preparedStatement.setString(1, userData.getSurname());
            preparedStatement.setString(2, userData.getFirstName());
            preparedStatement.setString(3, userData.getPatronymic());
            preparedStatement.setString(4, userData.getEmail());
            preparedStatement.setString(5, userData.getPhone());
            preparedStatement.setString(6, userData.getPassword());
            preparedStatement.setString(7, userData.getSaltPassword());
            preparedStatement.executeUpdate();
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                userData.setId(rs.getLong(1));
                return userData;
            }
        } catch (SQLException e) {
            throw new ResourceMappingException(e);
        }
    }

    @Override
    public UserData findOrSave(UserData applicant) {
        return null;
    }
}
