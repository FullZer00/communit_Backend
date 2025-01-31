package com.example.communitapi.web.dto.client;

import com.example.communitapi.entities.company.Company;
import com.example.communitapi.entities.userData.UserData;
import com.example.communitapi.web.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Client DTO")
public class ClientDto {

    @Schema(description = "Client id", example = "1")
    @NotNull(message = "ID must be not null", groups = OnUpdate.class)
    private long id;
    @Schema(description = "User data id", example = "1")
    private long userDataId;
    @Schema(description = "Companies id", example = "1")
    private long companyId;
    @Schema(description = "Clients user data")
    @NotNull(message = "Applicant must be not null.")
    private UserData applicant;
    @Schema(description = "Clients company", example = "test@gmail.com")
    @NotNull(message = "Company must be not null.")
    private Company company;

}
