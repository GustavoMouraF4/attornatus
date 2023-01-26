package com.gustavomoura.attornatus.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AddressRequest {

    private UUID peopleId;
    private String backyard;
    private String cep;
    private String number;
    private String city;
    private Boolean primaryAddress;
}
