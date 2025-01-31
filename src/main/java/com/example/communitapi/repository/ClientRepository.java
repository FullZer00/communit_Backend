package com.example.communitapi.repository;

import com.example.communitapi.entities.client.Client;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ClientRepository {

    Optional<Client> findById(Long id);

    Optional<List<Client>> findAll();

    Optional<Client> findByEmail(String email);

    Optional<List<Client>> findAllByFullName(String fullName);

    boolean checkProjectOwner(@Param("projectId") long projectId, @Param("clientId") long clientId);

    void save(Client client);

    void update(Client client);

    void delete(Long id);
}
