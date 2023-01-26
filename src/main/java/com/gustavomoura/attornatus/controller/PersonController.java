package com.gustavomoura.attornatus.controller;

import com.gustavomoura.attornatus.dto.*;
import com.gustavomoura.attornatus.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;
    private final PersonMapper personMapper;

    @RequestMapping(path = "/v1/person", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonResponse> createPerson(@RequestBody PersonRequest request) {
        var entityToCreate = personMapper.toEntity(request);

        return new ResponseEntity<>(personMapper.toResponse(this.personService.createPerson(entityToCreate)),
                HttpStatus.CREATED);
    }

    @RequestMapping(path = "/v1/person/address", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompletePersonResponse> createCompletePerson(@RequestBody CompletePersonRequest request) {
        var personToCreate = personMapper.toPersonEntity(request);
        var addressToCreate = personMapper.toAddressEntity(request);

        return new ResponseEntity<>(personMapper.toResponse(this.personService.createCompletePerson(personToCreate,
                                                                                                    addressToCreate)),
                HttpStatus.CREATED);
    }

    @RequestMapping(path = "/v1/person/{id}", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonResponse> updatePerson(@PathVariable("id") String personId,
                                                       @RequestBody PersonRequest request) {
        var person = personMapper.toEntity(request);

        return new ResponseEntity<>(personMapper.toResponse(this.personService.updatePerson(personId,
                                                                                            person)),
                HttpStatus.ACCEPTED);
    }

    @RequestMapping(path = "/v1/person/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonResponse> getPerson(@PathVariable("name") String name) {
        return new ResponseEntity<>(personMapper.toResponse(this.personService.findPersonByName(name)), HttpStatus.OK);
    }

    @RequestMapping(path = "/v1/people", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonResponse>> findAllPeople() {
        return new ResponseEntity<>(personService.findAllPeople().stream()
                .map(personMapper::toResponse)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }
}
