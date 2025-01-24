package com.example.communitapi.repository.impl;

import com.example.communitapi.entities.exceptions.ResourceMappingException;
import com.example.communitapi.entities.exceptions.ResourceNotFoundException;
import com.example.communitapi.entities.worker.Worker;
import com.example.communitapi.repository.DataSourceConfig;
import com.example.communitapi.repository.WorkerRepository;
import com.example.communitapi.repository.mappers.WorkerRowMapper;
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
public class WorkerRepositoryImpl implements WorkerRepository {

    private final DataSourceConfig dataSourceConfig;

    private final String SELECT_QUERY = """
            select
                w.id as id_worker,
                w.user_data_id as id_user_data,
                ud.surname as surname_user_data,
                ud.first_name as first_name_user_data,
                ud.patronymic as patronymic_user_data,
                ud.email as email_user_data,
                ud.phone as phone_user_data,
                w.passport_number as passport_number_worker,
                w.passport_seria as passport_seria_worker,
                w.rate as rate_worker
            from communit.workers w
            inner join communit.user_data ud on ud.id = w.user_data_id
            """;

    private final String FIND_BY_ID = SELECT_QUERY + """
            where w.id = ?
            """;

    private final String FIND_BY_FULL_NAME = """
            where ud.surname || ' ' || ud.first_name || ' ' || COALESCE(ud.patronymic, ' ') ilike '%' || ? || '%';
            """;

    private final String FIND_ALL = SELECT_QUERY;

    private final String FIND_BY_EMAIL = SELECT_QUERY + """
            where ud.email = ?
            """;

    private final String FIND_ALL_BY_PROJECT_ID = SELECT_QUERY + """
            left join communit.projects_workers pr_w on pr_w.worker_id = w.id
            inner join communit.projects pr on pr.id = pr_w.project_id
            where pr.id = ?;
            """;

    private final String CREATE = """
            insert into communit.workers (user_data_id, passport_seria, passport_number, rate)
            values (?, ?, ?, ?);
            """;

    private final String UPDATE = """
            update communit.workers
                set passport_seria = ?,
                    passport_number = ?,
                    rate = ?
            where id = ?;
            """;

    private final String DELETE = """
            delete from communit.workers where id = ?;
            """;

    private final String GET_ID_USER_DATA_BY_ID_WORKER = """
        select w.user_data_id as id_user_data from workers w where id = ?
    """;

    private final String INSERT_ROLE_WORKER = """
            insert into users_roles(user_id, role_id)
                values(?, ?)
            """;

    @Override
    public Optional<Worker> findById(long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                return Optional.ofNullable(WorkerRowMapper.mapRow(rs));
            }
        }
        catch (SQLException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public Optional<List<Worker>> findByFullName(String fullName) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_FULL_NAME,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, fullName);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.of(WorkerRowMapper.mapRows(rs));
            }
        }
        catch (SQLException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public Optional<List<Worker>> findAll() {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.of(WorkerRowMapper.mapRows(rs));
            }
        }
        catch (SQLException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public Optional<Worker> findByEmail(String email) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                return Optional.of(WorkerRowMapper.mapRow(rs));
            }
        }
        catch (SQLException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public Optional<List<Worker>> findAllByProjectId(long projectId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_PROJECT_ID,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, projectId);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.of(WorkerRowMapper.mapRows(rs));
            }
        }
        catch (SQLException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @Override
    public Worker save(Worker worker) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE);
            statement.setLong(1, worker.getUserDataId());
            statement.setString(2, worker.getPassportNumber());
            statement.setString(3, worker.getPassportSeria());
            statement.setDouble(4, worker.getRate());
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                worker.setId(rs.getLong(1));
                return worker;
            }
        }
        catch (SQLException e) {
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public void update(Worker worker) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, worker.getPassportSeria());
            statement.setString(2, worker.getPassportNumber());
            statement.setDouble(3, worker.getRate());
            statement.setLong(4, worker.getId());
            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new ResourceMappingException(e.getMessage());
        }
    }

    @Override
    public void insertWorkerRole(long dataUserId, long roleId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_ROLE_WORKER);
            statement.setLong(1, dataUserId);
            statement.setLong(2, roleId);
            statement.executeUpdate();
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
}
