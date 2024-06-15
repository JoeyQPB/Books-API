package com.joey.Books_API.repositories;

import com.joey.Books_API.models.ReviewModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewRepository extends CrudRepository<ReviewModel, UUID> {
}
