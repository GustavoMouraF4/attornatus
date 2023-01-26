package com.gustavomoura.attornatus.repository;

import com.gustavomoura.attornatus.model.PeopleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@EnableJpaRepositories
public interface PeopleRepository extends CrudRepository<PeopleEntity, UUID> {

    @Query("SELECT p FROM PeopleEntity p WHERE p.name = :name")
    Optional<PeopleEntity> findPeopleByName(@Param("name") String name);

    Optional<PeopleEntity> findPeopleById(UUID peopleId);

    List<PeopleEntity> findAll();
}
