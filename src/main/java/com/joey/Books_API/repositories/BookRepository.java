package com.joey.Books_API.repositories;

import com.joey.Books_API.models.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<BookModel, UUID> {

    @Query("SELECT * FROM author_tb a WHERE a.name = :name")
    Optional<BookModel> findByName (@Param("name") String name);
}
