package com.example.communitapi.web.controllers;

import com.example.communitapi.entities.client.Client;
import com.example.communitapi.service.ClientService;
import com.example.communitapi.web.dto.client.ClientDto;
import com.example.communitapi.web.mappers.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.path}/clients")
@RequiredArgsConstructor
@Validated
public class ClientController {

    private final ClientService clientService;

    private final ClientMapper clientMapper;

    @GetMapping("/{id}")
    public ClientDto getById(@PathVariable long id) {
        Client client = clientService.getById(id);

        return clientMapper.toDto(client);
    }

    @GetMapping
    public List<ClientDto> getAll(@RequestBody String filter) {
        List<Client> clients = clientService.getAllClients(filter);

        return clientMapper.toDto(clients);
    }

    @PutMapping("/{id}")
    public ClientDto update(@PathVariable long id, @RequestBody ClientDto clientDto) {
        Client client = clientMapper.toEntity(clientDto);
        Client updatedClient = clientService.update(id, client);
        return clientMapper.toDto(updatedClient);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        clientService.deleteById(id);
    }
}
