package com.example.communitapi.service.Impl;

import com.example.communitapi.entities.client.Client;
import com.example.communitapi.entities.exceptions.ResourceNotFoundException;
import com.example.communitapi.repository.ClientRepository;
import com.example.communitapi.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    @Transactional(readOnly = true)
    public Client getById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Client getByEmail(String email) {
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> getAllClients(String filter) {
        return clientRepository.findAll()
                .orElseThrow(() -> new ResourceNotFoundException("Clients not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> getAllByFullName(String fullName) {
        return clientRepository.findAllByFullName(fullName)
                .orElseThrow(() -> new ResourceNotFoundException("Clients not found"));
    }

    @Override
    @Transactional
    public Client update(long id, Client client) {
        clientRepository.update(client);
        return client;
    }

    @Override
    @Transactional
    public Client create(Client client) {
        clientRepository.save(client);
        return client;
    }

    @Override
    public boolean isProjectOwner(long projectId, long clientId) {
        return clientRepository.checkProjectOwner(projectId, clientId);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        clientRepository.delete(id);
    }
}
