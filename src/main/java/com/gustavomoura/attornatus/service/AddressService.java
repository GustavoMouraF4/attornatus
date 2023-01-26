package com.gustavomoura.attornatus.service;

import com.gustavomoura.attornatus.model.AddressEntity;
import com.gustavomoura.attornatus.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressEntity createAddress(AddressEntity entity) {
        entity.setId(UUID.randomUUID());
        return addressRepository.save(entity);
    }

    public List<AddressEntity> findAllAddress(UUID peopleId) {
        return addressRepository.findAllByPeopleId(peopleId);
    }


    public void setAddressToPrimary(UUID peopleId, UUID addressId) {
        var addresses = findAllAddress(peopleId);

        addresses.stream().filter(AddressEntity::getPrimaryAddress)
                .map(address -> {
                    address.setPrimaryAddress(false);

                    addressRepository.save(address);

                    return address;
                })
                .filter(address -> address.getId().equals(addressId))
                .map(address -> {
                    address.setPrimaryAddress(true);

                    return addressRepository.save(address);
                });
    }
}
