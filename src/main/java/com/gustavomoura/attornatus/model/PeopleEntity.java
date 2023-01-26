package com.gustavomoura.attornatus.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "people")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeopleEntity {

    @Id
    @Column(name = "people_id")
    private UUID id;

    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;
}
