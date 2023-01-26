package com.gustavomoura.attornatus.controller;

import com.gustavomoura.attornatus.dto.*;
import com.gustavomoura.attornatus.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class AddressController {

    private final PeopleMapper mapper;
    private final AddressService addressService;

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/v1/address", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressResponse> createAddress(@RequestBody AddressRequest request) {
        var entityToCreate = mapper.toEntity(request);

        return new ResponseEntity<>(mapper.toResponse(this.addressService.createAddress(entityToCreate)),
                HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/v1/address/{peopleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AddressResponse>> findAllAddress(@PathVariable("peopleId") String peopleId) {
        return new ResponseEntity<>(addressService.findAllAddress(peopleId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/v1/address/{peopleId}/{addressId}", method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> setAddressToPrimary(@PathVariable("peopleId") String peopleId,
                                                    @PathVariable("addressId") String addressId) {
        var addressEntities = this.addressService.findAllAddress(peopleId);
        this.addressService.setAddressToPrimary(addressId, addressEntities);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
