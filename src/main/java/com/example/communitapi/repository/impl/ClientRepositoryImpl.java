package com.example.communitapi.repository.impl;

import com.example.communitapi.entities.client.Client;
import com.example.communitapi.repository.ClientRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepositoryImpl implements ClientRepository {
    @Override
    public Optional<Client> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Client>> findAll() {
        return Optional.empty();
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Client>> findAllByFullName(String fullName) {
        return Optional.empty();
    }

    @Override
    public void save(Client client) {

    }

    @Override
    public void update(Long id, Client client) {

    }

    @Override
    public void delete(Long id) {

    }
}
