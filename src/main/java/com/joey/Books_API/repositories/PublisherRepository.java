package com.joey.Books_API.repositories;

import com.joey.Books_API.models.PublisherModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PublisherRepository extends CrudRepository<PublisherModel, UUID> {
    Optional<PublisherModel> findByName (String name);
}
