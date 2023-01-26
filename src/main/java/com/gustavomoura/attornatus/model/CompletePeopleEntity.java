package com.gustavomoura.attornatus.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class CompletePeopleEntity {

    private UUID peopleId;
    private String name;
    private LocalDate birthDate;
    private UUID addressId;
    private String backyard;
    private String cep;
    private String number;
    private String city;
    private Boolean primaryAddress;
}
