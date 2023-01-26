package com.gustavomoura.attornatus.utils;

import com.gustavomoura.attornatus.model.AddressEntity;
import com.gustavomoura.attornatus.model.CompletePersonEntity;
import com.gustavomoura.attornatus.model.PersonEntity;
import org.springframework.stereotype.Component;

@Component
public class PersonFactory {

    public static CompletePersonEntity createCompletePersonEntity(PersonEntity person, AddressEntity address) {
        return CompletePersonEntity.builder()
                .personId(person.getId())
                .name(person.getName())
                .birthDate(person.getBirthDate())
                .addressId(address.getId())
                .backyard(address.getBackyard())
                .cep(address.getCep())
                .number(address.getNumber())
                .city(address.getCity())
                .primaryAddress(address.getPrimaryAddress())
                .build();
    }
}
