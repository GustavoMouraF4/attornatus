package com.gustavomoura.attornatus.controller;

import com.gustavomoura.attornatus.dto.*;
import com.gustavomoura.attornatus.service.AddressService;
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
public class AddressController {

    private final PersonMapper mapper;
    private final AddressService addressService;

    @RequestMapping(path = "/v1/address", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressResponse> createAddress(@RequestBody AddressRequest request) {
        var entityToCreate = mapper.toEntity(request);

        return new ResponseEntity<>(mapper.toResponse(this.addressService.createAddress(entityToCreate)),
                HttpStatus.CREATED);
    }

    @RequestMapping(path = "/v1/address/{personId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AddressResponse>> findAllAddress(@PathVariable("personId") String personId) {
        return new ResponseEntity<>(addressService.findAllAddress(personId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @RequestMapping(path = "/v1/address/{personId}/{addressId}", method = RequestMethod.PATCH,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> setAddressToPrimary(@PathVariable("personId") String personId,
                                                    @PathVariable("addressId") String addressId) {
        var addressEntities = this.addressService.findAllAddress(personId);
        this.addressService.setAddressToPrimary(addressId, addressEntities);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
