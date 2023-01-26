package com.gustavomoura.attornatus.service;

import com.gustavomoura.attornatus.exception.PersonNotFoundException;
import com.gustavomoura.attornatus.model.AddressEntity;
import com.gustavomoura.attornatus.model.PersonEntity;
import com.gustavomoura.attornatus.repository.PersonRepository;
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

class PersonServiceTest {

    private PersonRepository personRepository;
    private AddressService addressService;
    private PersonService personService;

    @BeforeEach
    void setUp() {
        personRepository = mock(PersonRepository.class);
        addressService = mock(AddressService.class);
        personService = new PersonService(personRepository, addressService);
    }

    @Test
    void shouldCreatePerson() {
        var personId = UUID.randomUUID().toString();
        var personEntity = createPersonEntity();

        when(personRepository.save(personEntity))
                .thenAnswer(invocation -> {
                    PersonEntity person = invocation.getArgument(0);
                    person.setId(personId);

                    return person;
                });

        var result = personService.createPerson(personEntity);

        assertEquals(result.getId(), personId);
        assertEquals(result.getName(), personEntity.getName());
        assertEquals(result.getBirthDate(), personEntity.getBirthDate());
    }

    @Test
    void shouldCreateCompletePerson() {
        var addressId = UUID.randomUUID().toString();
        var personId = UUID.randomUUID().toString();
        var address = createAddress(personId);
        var person = createPersonEntity();
        address.setId(null);

        when(personRepository.save(person))
                .thenAnswer(invocation -> {
                    PersonEntity entity = invocation.getArgument(0);
                    entity.setId(personId);

                    return entity;
                });

        when(addressService.createAddress(address))
                .thenAnswer(invocation -> {
                    AddressEntity entity = invocation.getArgument(0);
                    entity.setId(addressId);
                    entity.setPersonId(personId);

                    return entity;
                });

        var result = personService.createCompletePerson(person, address);

        assertEquals(person.getId(), personId);
        assertEquals(person.getName(), result.getName());
        assertEquals(person.getBirthDate(), result.getBirthDate());
        assertEquals(address.getId(), result.getAddressId());
        assertEquals(address.getCity(), result.getCity());
        assertEquals(address.getNumber(), result.getNumber());
        assertEquals(address.getCep(), result.getCep());
        assertEquals(address.getBackyard(), result.getBackyard());
        assertTrue(address.getPrimaryAddress());
    }

    @Test
    void shouldFindPersonByName() {
        var personId = UUID.randomUUID().toString();
        var person = createPersonEntity();
        person.setId(personId);

        when(personRepository.findPersonByName(person.getName()))
                .thenReturn(Optional.of(person));

        var result = personService.findPersonByName(person.getName());

        assertEquals(result.getId(), person.getId());
        assertEquals(result.getName(), person.getName());
        assertEquals(result.getBirthDate(), person.getBirthDate());
    }

    @Test
    void shouldThrowFindPersonByName() {
        var personId = UUID.randomUUID().toString();
        var person = createPersonEntity();
        person.setId(personId);

        when(personRepository.findPersonByName(person.getName()))
                .thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> personService.findPersonByName(person.getName()));
    }

    @Test
    void shouldFindPersonById() {
        var personId = UUID.randomUUID().toString();
        var person = createPersonEntity();
        person.setId(personId);

        when(personRepository.findById(person.getId()))
                .thenReturn(Optional.of(person));

        var result = personService.findPersonById(person.getId());

        assertEquals(result.getId(), person.getId());
        assertEquals(result.getName(), person.getName());
        assertEquals(result.getBirthDate(), person.getBirthDate());
    }

    @Test
    void shouldThrowFindPersonById() {
        var personId = UUID.randomUUID().toString();
        var person = createPersonEntity();
        person.setId(personId);

        when(personRepository.findById(person.getId()))
                .thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> personService.findPersonById(person.getId()));
    }

    @Test
    void shouldFindAllPeople() {
        var firstPersonId = UUID.randomUUID().toString();
        var firstPerson = createPersonEntity();
        firstPerson.setId(firstPersonId);
        var secondPersonId = UUID.randomUUID().toString();
        var secondPerson = createPersonEntity();
        secondPerson.setId(secondPersonId);

        var people = List.of(firstPerson, secondPerson);

        when(personRepository.findAll()).thenReturn(people);

        var result = personService.findAllPeople();

        assertTrue(result.stream().allMatch(person -> Objects.nonNull(person.getId()) &&
                                                      Objects.nonNull(person.getName()) &&
                                                      Objects.nonNull(person.getBirthDate())));
    }

    @Test
    void shouldUpdatePerson() {
        var personId = UUID.randomUUID().toString();
        var personToUpdate = createPersonEntity();
        personToUpdate.setId(personId);
        var person = PersonEntity.builder()
                .id(personId)
                .name("Unit test 2")
                .birthDate(LocalDate.MAX)
                .build();


        when(personRepository.findById(personToUpdate.getId()))
                .thenReturn(Optional.of(person));

        when(personRepository.save(person)).thenReturn(person);

        var result = personService.updatePerson(personId, person);

        assertEquals(result.getId(), person.getId());
        assertEquals(result.getName(), person.getName());
        assertEquals(result.getBirthDate(), person.getBirthDate());
    }

    private PersonEntity createPersonEntity() {
        return PersonEntity.builder()
                .name("Unit Test")
                .birthDate(LocalDate.EPOCH)
                .build();
    }

    private AddressEntity createAddress(String personId) {
        return AddressEntity.builder()
                .personId(personId)
                .city("Porto Alegre")
                .number("12345")
                .cep("91096340")
                .backyard("Backyard")
                .primaryAddress(true)
                .build();
    }
}