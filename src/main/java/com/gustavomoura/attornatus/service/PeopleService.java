package com.gustavomoura.attornatus.service;

import com.gustavomoura.attornatus.exception.PeopleNotFoundException;
import com.gustavomoura.attornatus.model.AddressEntity;
import com.gustavomoura.attornatus.model.CompletePeopleEntity;
import com.gustavomoura.attornatus.model.PeopleEntity;
import com.gustavomoura.attornatus.repository.PeopleRepository;
import com.gustavomoura.attornatus.utils.PeopleFactory;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final AddressService addressService;
    public PeopleEntity createPeople(PeopleEntity entityToCreate) {
        entityToCreate.setId(UUID.randomUUID().toString());
        return peopleRepository.save(entityToCreate);
    }

    public CompletePeopleEntity createCompletePeople(PeopleEntity people, AddressEntity address) {
        people.setId(UUID.randomUUID().toString());
        var peopleCreated = peopleRepository.save(people);

        address.setId(UUID.randomUUID().toString());
        address.setPeopleId(peopleCreated.getId());
        var addressCreated = addressService.createAddress(address);


        return PeopleFactory.createCompletePeopleEntity(peopleCreated, addressCreated);
    }

    @SneakyThrows
    public PeopleEntity findPeopleByName(String name) {
        return peopleRepository.findPeopleByName(name).orElseThrow(() ->
                new PeopleNotFoundException("Not found people with name: " + name));
    }


    public PeopleEntity findPeopleById(String peopleId) {
        return peopleRepository.findById(peopleId).orElseThrow(() ->
                new PeopleNotFoundException("Not found people with id: " + peopleId));
    }

    public List<PeopleEntity> findAllPeoples() {
        return peopleRepository.findAll();
    }

    public PeopleEntity updatePeople(String peopleId, PeopleEntity people) {
        var peopleToUpdate = findPeopleById(peopleId);

        peopleToUpdate.setName(people.getName());
        peopleToUpdate.setBirthDate(people.getBirthDate());

        return peopleRepository.save(peopleToUpdate);
    }
}
