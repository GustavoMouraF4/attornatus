package com.gustavomoura.attornatus.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PeopleResponse {

    private String id;
    private String name;
    private LocalDate birthDate;
}
