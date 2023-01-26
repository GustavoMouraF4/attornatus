package com.gustavomoura.attornatus.dto;

import com.gustavomoura.attornatus.model.AddressEntity;
import com.gustavomoura.attornatus.model.CompletePersonEntity;
import com.gustavomoura.attornatus.model.PersonEntity;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    public PersonResponse toResponse(PersonEntity entity) {
        return PersonResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .birthDate(entity.getBirthDate())
                .build();
    }

    public AddressResponse toResponse(AddressEntity entity) {
        return AddressResponse.builder()
                .id(entity.getId())
                .personId(entity.getPersonId())
                .backyard(entity.getBackyard())
                .cep(entity.getCep())
                .number(entity.getNumber())
                .city(entity.getCity())
                .primaryAddress(entity.getPrimaryAddress())
                .build();
    }

    public CompletePersonResponse toResponse(CompletePersonEntity entity) {
        return CompletePersonResponse.builder()
                .personId(entity.getPersonId())
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

    public PersonEntity toPersonEntity(CompletePersonRequest entity) {
        return PersonEntity.builder()
                .name(entity.getName())
                .birthDate(entity.getBirthDate())
                .build();
    }

    public AddressEntity toAddressEntity(CompletePersonRequest entity) {
        return AddressEntity.builder()
                .backyard(entity.getBackyard())
                .cep(entity.getCep())
                .number(entity.getNumber())
                .city(entity.getCity())
                .primaryAddress(entity.getPrimaryAddress())
                .build();
    }

    public PersonEntity toEntity(PersonRequest request) {
        return PersonEntity.builder()
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .build();
    }

    public AddressEntity toEntity(AddressRequest request) {
        return AddressEntity.builder()
                .personId(request.getPersonId())
                .backyard(request.getBackyard())
                .cep(request.getCep())
                .number(request.getNumber())
                .city(request.getCity())
                .primaryAddress(false)
                .build();
    }

}
