package com.gustavomoura.attornatus.repository;

import com.gustavomoura.attornatus.model.AddressEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, String> {

    List<AddressEntity> findAllByPeopleId(String peopleId);
}
