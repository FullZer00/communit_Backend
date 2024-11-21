package com.example.communitapi.service;

import com.example.communitapi.entities.client.Client;

import java.util.List;


public interface ClientService {
    Client getById(Long id);

    Client getByEmail(String email);

    List<Client> getAllClients(String filter);

    List<Client> getAllByFullName(String fullName);

    Client update(long id, Client client);

    Client create(Client client);

    void deleteById(long id);
}
