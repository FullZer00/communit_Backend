package com.example.communitapi.web.mappers;

import com.example.communitapi.entities.client.Client;
import com.example.communitapi.web.dto.client.ClientDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDto toDto(Client client);

    List<ClientDto> toDto(List<Client> clients);

    Client toEntity(ClientDto clientDto);

}
