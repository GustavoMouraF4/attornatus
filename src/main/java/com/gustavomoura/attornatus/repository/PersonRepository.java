package com.gustavomoura.attornatus.repository;

import com.gustavomoura.attornatus.model.PersonEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository<PersonEntity, String> {

    @Query("SELECT p FROM PersonRepository p WHERE p.name = :name")
    Optional<PersonEntity> findPersonByName(@Param("name") String name);

    @Query("SELECT p FROM PersonRepository p")
    List<PersonEntity> findAll();
}
