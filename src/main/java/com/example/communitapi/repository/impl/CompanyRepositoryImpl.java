package com.example.communitapi.repository.impl;

import com.example.communitapi.entities.company.Company;
import com.example.communitapi.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepository {
    @Override
    public Optional<Company> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Company>> findAll() {
        return Optional.empty();
    }

    @Override
    public Optional<List<Company>> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Company> save(Company company) {
        return Optional.empty();
    }

    @Override
    public void delete(Company company) {

    }

    @Override
    public Company update(long id, Company company) {
        return null;
    }

    @Override
    public Company findOrSave(Company company) {
        return null;
    }
}
