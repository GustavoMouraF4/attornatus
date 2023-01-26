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

    /**
     * Create address to a person
     * @param entity
     * @return address created
     */
    public AddressEntity createAddress(AddressEntity entity) {
        entity.setId(UUID.randomUUID().toString());
        return addressRepository.save(entity);
    }

    /**
     * Find all of a person's addresses
     * @param personId
     * @return List with all of a person's addresses
     */
    public List<AddressEntity> findAllAddress(String personId) {
        return addressRepository.findAllByPersonId(personId);
    }


    /**
     * This method exchanges the person's main address, leaving the old one as false and the new as true
     * @param addressIdToUpdate
     * @param addressEntities List with all of a person's addresses
     * @return List of all addresses of a person to facilitate unitary testing
     */
    public List<AddressEntity> setAddressToPrimary(String addressIdToUpdate, List<AddressEntity> addressEntities) {
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
