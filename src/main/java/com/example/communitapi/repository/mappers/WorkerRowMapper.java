package com.example.communitapi.repository.mappers;

import com.example.communitapi.entities.worker.Worker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkerRowMapper {

    public static Worker mapRow(ResultSet rs) throws SQLException {
        if (rs.next()) {
            Worker worker = new Worker();
            worker.setId(rs.getLong("id_worker"));
            worker.setRate(rs.getDouble("rate_worker"));
            worker.setPassportNumber(String.valueOf(rs.getInt("passport_number_worker")));
            worker.setPassportSeria(String.valueOf(rs.getInt("passport_seria_worker")));
            worker.setUserDataId(rs.getLong("id_user_data_worker"));
            return worker;
        }
        return null;
    }

    public static List<Worker> mapRows(ResultSet rs) throws SQLException {
        List<Worker> workers = new ArrayList<>();

        while (rs.next()) {
            Worker worker = mapRow(rs);
            workers.add(worker);
        }

        return workers;
    }
}
