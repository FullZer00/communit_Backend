package com.example.communitapi.web.mappers;

import com.example.communitapi.entities.userData.UserData;
import com.example.communitapi.web.dto.userData.UserDataDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDataMapper {

    UserDataDto toDto(UserData userData);
    List<UserDataDto> toDto(List<UserData> userData);

    UserData fromDto(UserDataDto userDataDto);
    List<UserData> fromDto(List<UserDataDto> userDataDto);

}
