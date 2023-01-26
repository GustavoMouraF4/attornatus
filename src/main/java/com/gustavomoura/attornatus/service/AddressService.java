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


    public List<AddressEntity> setAddressToPrimary(UUID addressIdToUpdate, List<AddressEntity> addressEntities) {
        addressEntities.stream().filter(AddressEntity::getPrimaryAddress)
                .forEach(address -> {
                    address.setPrimaryAddress(false);
                    addressRepository.save(address);
                });

        addressEntities.stream().filter(address -> address.getId().equals(addressIdToUpdate))
                .forEach(address -> {
                    address.setPrimaryAddress(true);
                    addressRepository.save(address);
                });

        return addressEntities;
    }
}
