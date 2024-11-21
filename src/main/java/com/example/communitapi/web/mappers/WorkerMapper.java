package com.example.communitapi.web.mappers;

import com.example.communitapi.entities.worker.Worker;
import com.example.communitapi.web.dto.worker.WorkerDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkerMapper {

    WorkerDto toDto(Worker worker);

    List<WorkerDto> toDto(List<Worker>worker);

    Worker toEntity(WorkerDto workerDto);
}
