package com.gustavomoura.attornatus.service;

import com.gustavomoura.attornatus.model.AddressEntity;
import com.gustavomoura.attornatus.model.PeopleEntity;
import com.gustavomoura.attornatus.repository.PeopleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PeopleServiceTest {

    private PeopleRepository peopleRepository;
    private AddressService addressService;
    private PeopleService peopleService;

    @BeforeEach
    void setUp() {
        peopleRepository = mock(PeopleRepository.class);
        addressService = mock(AddressService.class);
        peopleService = new PeopleService(peopleRepository, addressService);
    }

    @Test
    void shouldCreatePeople() {
        var peopleId = UUID.randomUUID();
        var peopleEntity = createPeopleEntity();

        when(peopleRepository.save(peopleEntity))
                .thenAnswer(invocation -> {
                    PeopleEntity people = invocation.getArgument(0);
                    people.setId(peopleId);

                    return people;
                });

        var result = peopleService.createPeople(peopleEntity);

        assertEquals(result.getId(), peopleId);
        assertEquals(result.getName(), peopleEntity.getName());
        assertEquals(result.getBirthDate(), peopleEntity.getBirthDate());
    }

    @Test
    void shouldCreateCompletePeople() {
        var addressId = UUID.randomUUID();
        var peopleId = UUID.randomUUID();
        var address = createAddress(peopleId);
        var people = createPeopleEntity();
        address.setId(null);

        when(peopleRepository.save(people))
                .thenAnswer(invocation -> {
                    PeopleEntity entity = invocation.getArgument(0);
                    entity.setId(peopleId);

                    return entity;
                });

        when(addressService.createAddress(address))
                .thenAnswer(invocation -> {
                    AddressEntity entity = invocation.getArgument(0);
                    entity.setId(addressId);
                    entity.setPeopleId(peopleId);

                    return entity;
                });

        var result = peopleService.createCompletePeople(people, address);

        assertEquals(people.getId(), peopleId);
        assertEquals(people.getName(), result.getName());
        assertEquals(people.getBirthDate(), result.getBirthDate());
        assertEquals(address.getId(), result.getAddressId());
        assertEquals(address.getCity(), result.getCity());
        assertEquals(address.getNumber(), result.getNumber());
        assertEquals(address.getCep(), result.getCep());
        assertEquals(address.getBackyard(), result.getBackyard());
        assertTrue(address.getPrimaryAddress());
    }

    @Test
    void shouldFindPeopleByName() {
        var peopleId = UUID.randomUUID();
        var people = createPeopleEntity();
        people.setId(peopleId);

        when(peopleRepository.findPeopleByName(people.getName()))
                .thenReturn(Optional.of(people));

        var result = peopleService.findPeopleByName(people.getName());

        assertEquals(result.getId(), people.getId());
        assertEquals(result.getName(), people.getName());
        assertEquals(result.getBirthDate(), people.getBirthDate());
    }

    @Test
    void shouldFindPeopleById() {
        var peopleId = UUID.randomUUID();
        var people = createPeopleEntity();
        people.setId(peopleId);

        when(peopleRepository.findPeopleById(people.getId()))
                .thenReturn(Optional.of(people));

        var result = peopleService.findPeopleById(people.getId());

        assertEquals(result.getId(), people.getId());
        assertEquals(result.getName(), people.getName());
        assertEquals(result.getBirthDate(), people.getBirthDate());
    }

    @Test
    void shouldFindAllPeoples() {
        var firstPeopleId = UUID.randomUUID();
        var firstPeople = createPeopleEntity();
        firstPeople.setId(firstPeopleId);
        var secondPeopleId = UUID.randomUUID();
        var secondPeople = createPeopleEntity();
        secondPeople.setId(secondPeopleId);

        var peoples = List.of(firstPeople, secondPeople);

        when(peopleRepository.findAll()).thenReturn(peoples);

        var result = peopleService.findAllPeoples();

        assertTrue(result.stream().allMatch(people -> Objects.nonNull(people.getId()) &&
                                                      Objects.nonNull(people.getName()) &&
                                                      Objects.nonNull(people.getBirthDate())));
    }

    @Test
    void shouldUpdatePeople() {
        var peopleId = UUID.randomUUID();
        var peopleToUpdate = createPeopleEntity();
        peopleToUpdate.setId(peopleId);
        var people = PeopleEntity.builder()
                .id(peopleId)
                .name("Unit test 2")
                .birthDate(LocalDate.MAX)
                .build();


        when(peopleRepository.findPeopleById(peopleToUpdate.getId()))
                .thenReturn(Optional.of(people));

        when(peopleRepository.save(people)).thenReturn(people);

        var result = peopleService.updatePeople(peopleId, people);

        assertEquals(result.getId(), people.getId());
        assertEquals(result.getName(), people.getName());
        assertEquals(result.getBirthDate(), people.getBirthDate());
    }

    private PeopleEntity createPeopleEntity() {
        return PeopleEntity.builder()
                .name("Unit Test")
                .birthDate(LocalDate.EPOCH)
                .build();
    }

    private AddressEntity createAddress(UUID peopleId) {
        return AddressEntity.builder()
                .peopleId(peopleId)
                .city("Porto Alegre")
                .number("12345")
                .cep("91096340")
                .backyard("Backyard")
                .primaryAddress(true)
                .build();
    }
}