package com.example.communitapi.repository;

import com.example.communitapi.entities.client.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {

    Optional<Client> findById(Long id);

    Optional<List<Client>> findAll();

    Optional<Client> findByEmail(String email);

    Optional<List<Client>> findAllByFullName(String fullName);

    Client save(Client client);

    Client update(Long id, Client client);

    void delete(Long id);
}
