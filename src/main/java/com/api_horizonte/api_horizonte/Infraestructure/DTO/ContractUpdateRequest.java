package com.api_horizonte.api_horizonte.Infraestructure.DTO;

import com.api_horizonte.api_horizonte.Infraestructure.Entities.ContractStatus;

public record ContractUpdateRequest(
        ContractStatus statusContract
) {}
