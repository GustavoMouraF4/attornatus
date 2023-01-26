package com.gustavomoura.attornatus.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "address")
@Data
@Builder
public class AddressEntity {

    @Id
    @Column(name = "address_id")
    private UUID id;

    @Column(name = "people_id")
    private UUID peopleId;

    private String backyard;
    private String cep;
    private String number;
    private String city;

    @Column(name = "primary_address")
    private Boolean primaryAddress;
}
