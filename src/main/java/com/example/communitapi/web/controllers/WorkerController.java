package com.example.communitapi.web.controllers;

import com.example.communitapi.entities.worker.Worker;
import com.example.communitapi.service.WorkerService;
import com.example.communitapi.web.dto.validation.OnCreate;
import com.example.communitapi.web.dto.validation.OnUpdate;
import com.example.communitapi.web.dto.worker.WorkerDto;
import com.example.communitapi.web.mappers.WorkerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.path}/workers")
@RequiredArgsConstructor
@Validated
public class WorkerController {

    private final WorkerService workerService;

    private final WorkerMapper workerMapper;


    @GetMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id, " +
            "T(com.example.communitapi.entities.role.Roles).ADMIN, " +
            "T(com.example.communitapi.entities.role.Roles).ARCHITECT)")
    public WorkerDto getById(@PathVariable long id) {
        Worker worker = workerService.getById(id);
        return workerMapper.toDto(worker);
    }

    @GetMapping
    public List<WorkerDto> getAll() {
        List<Worker> workers = workerService.getAll();

        return workerMapper.toDto(workers);
    }

    @GetMapping("/email")
    public WorkerDto getByEmail(@RequestParam String email) {
        Worker worker = workerService.getByEmail(email);

        return workerMapper.toDto(worker);
    }

    @PostMapping
    public WorkerDto create(@RequestBody @Validated(OnCreate.class) WorkerDto workerDto) {
        Worker worker = workerMapper.toEntity(workerDto);

        return workerMapper.toDto(workerService.create(worker));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        workerService.deleteById(id);
    }

    @PutMapping("/{id}")
    public WorkerDto updateById(@Validated(OnUpdate.class) @RequestBody WorkerDto workerDto, @PathVariable long id) {
        Worker worker = workerMapper.toEntity(workerDto);
        Worker updatedWorker = workerService.update(id, worker);

        return workerMapper.toDto(updatedWorker);
    }
}
