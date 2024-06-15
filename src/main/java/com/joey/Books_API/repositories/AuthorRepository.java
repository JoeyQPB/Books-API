package com.joey.Books_API.repositories;

import com.joey.Books_API.models.AuthorModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorModel, UUID> {
    Optional<AuthorModel> findByName (String name);

    @Query("SELECT a FROM author_tb a WHERE a.name IN :names")
    Set<AuthorModel> findAllByName(@Param("names") Set<String> names);
}
