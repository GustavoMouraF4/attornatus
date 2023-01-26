package com.gustavomoura.attornatus.dto;

import com.gustavomoura.attornatus.model.AddressEntity;
import com.gustavomoura.attornatus.model.CompletePeopleEntity;
import com.gustavomoura.attornatus.model.PeopleEntity;
import org.springframework.stereotype.Component;

@Component
public class PeopleMapper {

    public PeopleResponse toResponse(PeopleEntity entity) {
        return PeopleResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .birthDate(entity.getBirthDate())
                .build();
    }

    public AddressResponse toResponse(AddressEntity entity) {
        return AddressResponse.builder()
                .id(entity.getId())
                .peopleId(entity.getPeopleId())
                .backyard(entity.getBackyard())
                .cep(entity.getCep())
                .number(entity.getNumber())
                .city(entity.getCity())
                .primaryAddress(entity.getPrimaryAddress())
                .build();
    }

    public CompletePeopleResponse toResponse(CompletePeopleEntity entity) {
        return CompletePeopleResponse.builder()
                .peopleId(entity.getPeopleId())
                .name(entity.getName())
                .birthDate(entity.getBirthDate())
                .addressId(entity.getAddressId())
                .backyard(entity.getBackyard())
                .cep(entity.getCep())
                .number(entity.getNumber())
                .city(entity.getCity())
                .primaryAddress(entity.getPrimaryAddress())
                .build();
    }

    public PeopleEntity toPeopleEntity(CompletePeopleRequest entity) {
        return PeopleEntity.builder()
                .name(entity.getName())
                .birthDate(entity.getBirthDate())
                .build();
    }

    public AddressEntity toAddressEntity(CompletePeopleRequest entity) {
        return AddressEntity.builder()
                .backyard(entity.getBackyard())
                .cep(entity.getCep())
                .number(entity.getNumber())
                .city(entity.getCity())
                .primaryAddress(entity.getPrimaryAddress())
                .build();
    }

    public PeopleEntity toEntity(PeopleRequest request) {
        return PeopleEntity.builder()
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .build();
    }

    public AddressEntity toEntity(AddressRequest request) {
        return AddressEntity.builder()
                .peopleId(request.getPeopleId())
                .backyard(request.getBackyard())
                .cep(request.getCep())
                .number(request.getNumber())
                .city(request.getCity())
                .primaryAddress(request.getPrimaryAddress())
                .build();
    }

}
