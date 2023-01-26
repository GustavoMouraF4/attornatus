package com.gustavomoura.attornatus.service;

import com.gustavomoura.attornatus.model.AddressEntity;
import com.gustavomoura.attornatus.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddressServiceTest {

    private AddressRepository addressRepository;
    private AddressService addressService;

    @BeforeEach
    void setUp() {
        addressRepository = mock(AddressRepository.class);
        addressService = new AddressService(addressRepository);
    }

    @Test
    void shouldCreateAddress() {
        var addressId = UUID.randomUUID();
        var peopleId = UUID.randomUUID();
        var address = createAddress(peopleId);

        when(addressRepository.save(address))
                .thenAnswer(invocation -> {
                    AddressEntity addressSaved = invocation.getArgument(0);
                    addressSaved.setId(addressId);

                    return addressSaved;
                });

        var result = addressService.createAddress(address);

        assertEquals(result.getId(), addressId);
        assertEquals(result.getPeopleId(), peopleId);
        assertEquals(result.getCity(), address.getCity());
        assertEquals(result.getNumber(), address.getNumber());
        assertEquals(result.getCep(), address.getCep());
        assertEquals(result.getBackyard(), address.getBackyard());
        assertTrue(result.getPrimaryAddress());
    }

    @Test
    void shouldFindAllAddress() {
        var firstAddressId = UUID.randomUUID();
        var secondAddressId = UUID.randomUUID();
        var peopleId = UUID.randomUUID();
        var firstAddress = createAddress(peopleId);
        firstAddress.setId(firstAddressId);
        var secondAddress = createAddress(peopleId);
        secondAddress.setId(secondAddressId);
        secondAddress.setCity("São Paulo");
        secondAddress.setNumber("78901");
        secondAddress.setCep("97640890");

        when(addressRepository.findAllByPeopleId(peopleId))
                .thenReturn(List.of(firstAddress, secondAddress));

        var result = addressService.findAllAddress(peopleId);

        assertTrue(result.stream().allMatch(address -> Objects.nonNull(address.getId()) &&
                                                       Objects.nonNull(address.getPeopleId()) &&
                                                       Objects.nonNull(address.getCity()) &&
                                                       Objects.nonNull(address.getNumber()) &&
                                                       Objects.nonNull(address.getCep()) &&
                                                       Objects.nonNull(address.getBackyard()) &&
                                                       address.getPrimaryAddress()));
    }

    @Test
    void shouldSetAddressToPrimary() {
        var firstAddressId = UUID.randomUUID();
        var secondAddressId = UUID.randomUUID();
        var peopleId = UUID.randomUUID();
        var firstAddress = createAddress(peopleId);
        firstAddress.setId(firstAddressId);
        var secondAddress = createAddress(peopleId);
        secondAddress.setId(secondAddressId);
        secondAddress.setCity("São Paulo");
        secondAddress.setNumber("78901");
        secondAddress.setCep("97640890");
        secondAddress.setPrimaryAddress(false);

        var addressEntities = List.of(firstAddress, secondAddress);

        when(addressRepository.save(firstAddress))
                .thenAnswer(invocation -> {
                    AddressEntity address = invocation.getArgument(0);
                    address.setPrimaryAddress(false);

                    return address;
                });

        when(addressRepository.save(secondAddress))
                .thenAnswer(invocation -> {
                    AddressEntity address = invocation.getArgument(0);
                    address.setPrimaryAddress(true);

                    return address;
                });

        var result = addressService.setAddressToPrimary(secondAddressId, addressEntities);

        assertFalse(result.get(0).getPrimaryAddress());
        assertTrue(result.get(1).getPrimaryAddress());
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