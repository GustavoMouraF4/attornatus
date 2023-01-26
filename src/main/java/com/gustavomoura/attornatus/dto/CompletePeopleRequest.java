package com.gustavomoura.attornatus.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CompletePeopleRequest {

    private String name;
    private LocalDate birthDate;
    private String backyard;
    private String cep;
    private String number;
    private String city;
    private Boolean primaryAddress;
}
