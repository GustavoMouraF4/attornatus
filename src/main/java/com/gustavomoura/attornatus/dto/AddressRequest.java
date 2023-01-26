package com.gustavomoura.attornatus.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressRequest {

    private String peopleId;
    private String backyard;
    private String cep;
    private String number;
    private String city;
    private Boolean primaryAddress;
}
