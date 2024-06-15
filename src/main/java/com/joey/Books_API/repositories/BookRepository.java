package com.joey.Books_API.repositories;

import com.joey.Books_API.models.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<BookModel, UUID> {
    @Query("SELECT b FROM book_tb b WHERE b.name = :name")
    Optional<BookModel> findByName (@Param("name") String name);

    @Query("SELECT b FROM book_tb b JOIN b.authors a WHERE a.name = :authorName")
    List<BookModel> findBooksByAuthorName(@Param("authorName") String authorName);

//    @Query("SELECT b FROM book_tb b JOIN b.publisher p WHERE p.name = :publisherName")
//    Set<BookModel> findBooksByPublisherName(@Param("publisherName") String publisherName);
}
