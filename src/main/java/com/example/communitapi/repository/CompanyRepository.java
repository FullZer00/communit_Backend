package com.example.communitapi.repository;

import com.example.communitapi.entities.company.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository {

    Optional<Company> findById(long id);

    Optional<List<Company>> findAll();

    Optional<List<Company>> findByName(String name);

    Optional<Company> save(Company company);

    void delete(Company company);

    Company update(long id, Company company);

    Company findOrSave(Company company);
}
