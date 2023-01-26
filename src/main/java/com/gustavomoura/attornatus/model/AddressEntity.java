package com.gustavomoura.attornatus.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "address")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity {

    @Id
    @Column(name = "address_id")
    private String id;

    @Column(name = "people_id")
    private String peopleId;

    private String backyard;
    private String cep;
    private String number;
    private String city;

    @Column(name = "primary_address")
    private Boolean primaryAddress;
}
