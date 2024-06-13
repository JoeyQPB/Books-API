package com.joey.Books_API.repositories;

import com.joey.Books_API.models.AuthorModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorModel, UUID> {
}
