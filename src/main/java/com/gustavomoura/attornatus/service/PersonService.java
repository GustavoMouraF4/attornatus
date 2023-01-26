package com.gustavomoura.attornatus.service;

import com.gustavomoura.attornatus.exception.PersonNotFoundException;
import com.gustavomoura.attornatus.model.AddressEntity;
import com.gustavomoura.attornatus.model.CompletePersonEntity;
import com.gustavomoura.attornatus.model.PersonEntity;
import com.gustavomoura.attornatus.repository.PersonRepository;
import com.gustavomoura.attornatus.utils.PersonFactory;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final AddressService addressService;

    /**
     * Create a person
     * @param entityToCreate
     * @return person created
     */
    public PersonEntity createPerson(PersonEntity entityToCreate) {
        entityToCreate.setId(UUID.randomUUID().toString());
        return personRepository.save(entityToCreate);
    }

    /**
     * this method creates a person and also creates an address for that person
     * @param person
     * @param address
     * @return
     */
    public CompletePersonEntity createCompletePerson(PersonEntity person, AddressEntity address) {
        person.setId(UUID.randomUUID().toString());
        var personCreated = personRepository.save(person);

        address.setId(UUID.randomUUID().toString());
        address.setPersonId(personCreated.getId());
        var addressCreated = addressService.createAddress(address);


        return PersonFactory.createCompletePersonEntity(personCreated, addressCreated);
    }

    /**
     * find a person by your name and if you do not find error returns
     * @param name
     * @return
     */
    @SneakyThrows
    public PersonEntity findPersonByName(String name) {
        return personRepository.findPersonByName(name).orElseThrow(() ->
                new PersonNotFoundException("Not found person with name: " + name));
    }

    /**
     * find a person by your id
     * @param personId
     * @return
     */
    public PersonEntity findPersonById(String personId) {
        return personRepository.findById(personId).orElseThrow(() ->
                new PersonNotFoundException("Not found person with id: " + personId));
    }

    /**
     * that method lists all people
     * @return
     */
    public List<PersonEntity> findAllPeople() {
        return personRepository.findAll();
    }

    /**
     * This method updates a person searching for it for their id
     * @param personId
     * @param person
     * @return
     */
    public PersonEntity updatePerson(String personId, PersonEntity person) {
        var personToUpdate = findPersonById(personId);

        personToUpdate.setName(person.getName());
        personToUpdate.setBirthDate(person.getBirthDate());

        return personRepository.save(personToUpdate);
    }
}
