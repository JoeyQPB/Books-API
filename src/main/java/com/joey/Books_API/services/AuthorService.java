package com.joey.Books_API.services;

import com.joey.Books_API.exceptions.ItemNotFoundException;
import com.joey.Books_API.models.AuthorModel;
import com.joey.Books_API.models.BookModel;
import com.joey.Books_API.models.dtos.AuthorDto;
import com.joey.Books_API.repositories.AuthorRepository;
import com.joey.Books_API.repositories.BookRepository;
import com.joey.Books_API.services.dto.ServiceResponse;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class AuthorService {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthorService.class);
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository,
                         BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    @Caching(
            put = { @CachePut(value = "authors", key = "#result.body().id") },
            evict = { @CacheEvict(value = "authors", key = "'allAuthors'") }
    )
    public ServiceResponse<AuthorModel> save (AuthorDto authorDto) {
        AuthorModel author = new AuthorModel();
        author.setName(authorDto.name());

        AuthorModel authorCreated = this.authorRepository.save(author);
        LOGGER.info("Author Created: {}", authorCreated);
        return new ServiceResponse<>(HttpStatus.CREATED, authorCreated);
    }

    @Cacheable(value = "authors", key = "#id")
    public ServiceResponse<AuthorModel> getById (UUID id) {
        AuthorModel author = this.authorRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Author not found for id: " + id));

        LOGGER.info("Author found: {}", author);
        return new ServiceResponse<>(HttpStatus.OK, author);
    }

    @Caching(
            put = { @CachePut(value = "authors", key = "#result.body().id") }
    )
    public ServiceResponse<AuthorModel> getByName (String name) {
        AuthorModel author = this.authorRepository.findByName(name)
                .orElseThrow(() -> new ItemNotFoundException("Author not found for name: " + name));

        LOGGER.info("Author found: {}", author);
        return new ServiceResponse<>(HttpStatus.OK, author);
    }

    @Cacheable(value = "authors", key = "'allAuthors'")
    public ServiceResponse<Iterable<AuthorModel>> getAllAuthors () {
        Iterable<AuthorModel> authors = this.authorRepository.findAll();
        LOGGER.info("Found all Authors");
        return new ServiceResponse<>(HttpStatus.OK, authors);
    }

    @Transactional
    public ServiceResponse<AuthorModel> update (UUID id, AuthorDto authorDto) {
        AuthorModel authorUpdated =  this.authorRepository.findById(id)
                .map(author -> {
                    author.setName(authorDto.name());
                    return this.authorRepository.save(author);
                })
                .orElseThrow(() -> new ItemNotFoundException("Author not found for: " + authorDto.name()));

        LOGGER.info("Author updated: {}", authorUpdated);
        return new ServiceResponse<>(HttpStatus.OK, authorUpdated);
    }

    @Transactional
    @Caching(
            put = { @CachePut(value = "authors", key = "#authorId") }
    )
    public ServiceResponse<AuthorModel> addBookToAuthor (UUID authorId, UUID bookId) {
        AuthorModel author =  this.authorRepository.findById(authorId)
                .orElseThrow(() -> new ItemNotFoundException("Author not found for: " + authorId));

        BookModel book = this.bookRepository.findById(bookId)
                .orElseThrow(() -> new ItemNotFoundException("Book not found for: " + bookId));

        Set<BookModel> books = author.getBooks();
        books.add(book);

        Set<AuthorModel> authors = book.getAuthors();
        authors.add(author);

        AuthorModel authorUpdated = this.authorRepository.save(author);
        BookModel bookUpdated = this.bookRepository.save(book);

        LOGGER.info("Author updated: {}", authorUpdated);
        LOGGER.info("Book updated: {}", bookUpdated);
        return new ServiceResponse<>(HttpStatus.OK, authorUpdated);
    }

    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = "authors", key = "#id"),
                    @CacheEvict(value = "authors", key = "'allAuthors'")
            }
    )
    public ServiceResponse<Boolean> delete (UUID id) {
        AuthorModel author = this.getById(id).body();
        this.authorRepository.delete(author);
        LOGGER.info("Author deleted: {}", id);
        return new ServiceResponse<>(HttpStatus.OK, true);
    }
}
