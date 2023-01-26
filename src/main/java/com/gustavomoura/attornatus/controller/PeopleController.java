package com.gustavomoura.attornatus.controller;

import com.gustavomoura.attornatus.dto.*;
import com.gustavomoura.attornatus.service.PeopleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class PeopleController {

    private final PeopleService peopleService;
    private final PeopleMapper peopleMapper;

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/v1", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PeopleResponse> createPeople(@RequestBody PeopleRequest request) {
        var entityToCreate = peopleMapper.toEntity(request);

        return new ResponseEntity<>(peopleMapper.toResponse(this.peopleService.createPeople(entityToCreate)),
                HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/v1/people", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CompletePeopleResponse> createCompletePeople(@RequestBody CompletePeopleRequest request) {
        var peopleToCreate = peopleMapper.toPeopleEntity(request);
        var addressToCreate = peopleMapper.toAddressEntity(request);

        return new ResponseEntity<>(peopleMapper.toResponse(this.peopleService.createCompletePeople(peopleToCreate,
                                                                                                    addressToCreate)),
                HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/v1/people/{id}", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PeopleResponse> updatePeople(@PathVariable("id") UUID peopleId,
                                                       @RequestBody PeopleRequest request) {
        var people = peopleMapper.toEntity(request);

        return new ResponseEntity<>(peopleMapper.toResponse(this.peopleService.updatePeople(peopleId,
                                                                                            people)),
                HttpStatus.ACCEPTED);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/v1/people/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PeopleResponse> getPeople(@PathVariable("name") String name) {
        return new ResponseEntity<>(peopleMapper.toResponse(this.peopleService.findPeopleByName(name)), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/v1/people", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PeopleResponse>> findAllPeoples() {
        return new ResponseEntity<>(peopleService.findAllPeoples().stream()
                .map(peopleMapper::toResponse)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }
}
