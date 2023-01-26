package com.gustavomoura.attornatus.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class PeopleResponse {

    private UUID id;
    private String name;
    private LocalDate birthDate;
}
