package com.gustavomoura.attornatus.utils;

import com.gustavomoura.attornatus.model.AddressEntity;
import com.gustavomoura.attornatus.model.CompletePeopleEntity;
import com.gustavomoura.attornatus.model.PeopleEntity;
import org.springframework.stereotype.Component;

@Component
public class PeopleFactory {

    public static CompletePeopleEntity createCompletePeopleEntity(PeopleEntity people, AddressEntity address) {
        return CompletePeopleEntity.builder()
                .peopleId(people.getId())
                .name(people.getName())
                .birthDate(people.getBirthDate())
                .addressId(address.getId())
                .backyard(address.getBackyard())
                .cep(address.getCep())
                .number(address.getNumber())
                .city(address.getCity())
                .primaryAddress(address.getPrimaryAddress())
                .build();
    }
}
