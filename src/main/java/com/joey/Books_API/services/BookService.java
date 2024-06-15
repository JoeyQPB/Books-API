package com.joey.Books_API.services;

import com.joey.Books_API.exceptions.ItemNotFoundException;
import com.joey.Books_API.models.AuthorModel;
import com.joey.Books_API.models.BookModel;
import com.joey.Books_API.models.PublisherModel;
import com.joey.Books_API.models.ReviewModel;
import com.joey.Books_API.models.dtos.BookModelDto;
import com.joey.Books_API.models.dtos.BookModelUpdateDto;
import com.joey.Books_API.repositories.AuthorRepository;
import com.joey.Books_API.repositories.BookRepository;
import com.joey.Books_API.repositories.PublisherRepository;
import com.joey.Books_API.repositories.ReviewRepository;
import com.joey.Books_API.services.dto.ServiceResponse;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final Logger LOGGER = LoggerFactory.getLogger(BookService.class);
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BookService (BookRepository bookRepository,
                        PublisherRepository publisherRepository,
                        AuthorRepository authorRepository,
                        ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
        this.authorRepository = authorRepository;
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public ServiceResponse<BookModel> save (BookModelDto bookModelDto) {
        BookModel book = new BookModel();
        book.setName(bookModelDto.title());

        PublisherModel publisher = this.publisherRepository.findByName(bookModelDto.publisherName())
                .orElseThrow(() -> new ItemNotFoundException("Publisher not found for: " + bookModelDto.publisherName()));

        Set<AuthorModel> authors = new HashSet<>(this.authorRepository.findAllByName(bookModelDto.authorNames()));

        if (authors.isEmpty() || authors.size() != bookModelDto.authorNames().size()) {
            Set<String> authorNames = authors.stream()
                    .map(AuthorModel::getName)
                    .collect(Collectors.toSet());

            Set<String> missingAuthors = bookModelDto.authorNames().stream()
                    .filter(name -> !authorNames.contains(name))
                    .collect(Collectors.toSet());

            throw new ItemNotFoundException("Author(s) not found! Missing: " + missingAuthors);
        }

        book.setPublisher(publisher);
        book.setAuthors(authors);

        ReviewModel reviewModel = new ReviewModel();

        if (bookModelDto.reviewComment().isEmpty()) reviewModel.setComment("");
        else reviewModel.setComment(bookModelDto.reviewComment());

        reviewModel.setBook(book);
        book.setReview(reviewModel);

        BookModel bookCreated = this.bookRepository.save(book);
        LOGGER.info("Book Created: {}", bookCreated);

        return new ServiceResponse<>(HttpStatus.CREATED, bookCreated);
    }

    public ServiceResponse<Iterable<BookModel>> getAllBooks() {
        List<BookModel> books = this.bookRepository.findAll();
        LOGGER.info("Finding all books!");
        return new ServiceResponse<>(HttpStatus.OK, books);
    }

    public ServiceResponse<Page<BookModel>> getAllPage (int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return new ServiceResponse<>(HttpStatus.OK, this.bookRepository.findAll(pageRequest));
    }

    public ServiceResponse<Page<BookModel>> getAllPageSortedByName (int page, int size) {
        Sort sort = Sort.by("name").ascending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return new ServiceResponse<>(HttpStatus.OK, this.bookRepository.findAll(pageRequest));
    }

    public ServiceResponse<BookModel> getByName (String title) {
        BookModel book = this.bookRepository.findByName(title)
                        .orElseThrow(() -> new ItemNotFoundException("Book not found!"));

        LOGGER.info("Book found: {}", book);
        return new ServiceResponse<>(HttpStatus.OK, book);
    }

    public ServiceResponse<BookModel> getById (UUID id) {
        BookModel book = this.bookRepository.findById(id)
                        .orElseThrow(() -> new ItemNotFoundException("Book not found!"));

        LOGGER.info("Book found: {}", book);
        return new ServiceResponse<>(HttpStatus.OK, book);
    }

    public ServiceResponse<Set<BookModel>> getBooksByAuthorName(String authorName) {
        this.authorRepository.findByName(authorName)
                .orElseThrow(() -> new ItemNotFoundException("Author not found! - " + authorName));

        Set<BookModel> books = new HashSet<>(this.bookRepository.findBooksByAuthorName(authorName));

        return new ServiceResponse<>(HttpStatus.OK, books);
    }

    public ServiceResponse<Set<BookModel>> getBooksByPublisher(String publisherName) {
        PublisherModel publisher = this.publisherRepository.findByName(publisherName)
                .orElseThrow(() -> new ItemNotFoundException("Publisher not found! - " + publisherName));

        return new ServiceResponse<>(HttpStatus.OK, publisher.getBooks());
    }

    @Transactional
    public ServiceResponse<BookModel> update (UUID id, BookModelUpdateDto dto) {
        BookModel book = this.getById(id).body();

        if (!dto.title().isEmpty()) book.setName(dto.title());

        if (!dto.authorNames().isEmpty()) {
            Set<AuthorModel> authors = new HashSet<>(this.authorRepository.findAllByName(dto.authorNames()));
            if (authors.isEmpty()) throw new ItemNotFoundException("Not Found all the Authors for: " + dto.publisherName());
            book.setAuthors(authors);
        }

        if (!dto.publisherName().isEmpty()) {
            Optional<PublisherModel> publisher = this.publisherRepository.findByName(dto.publisherName());
            if (publisher.isEmpty()) throw new ItemNotFoundException("Publisher not found for: " + dto.publisherName());
            book.setPublisher(publisher.get());
        }

        if (!dto.reviewComment().isEmpty()) {
            UUID reviewId = book.getReview().getId();
            Optional<ReviewModel> review = this.reviewRepository.findById(reviewId);
            if (review.isEmpty())
                throw new ItemNotFoundException("Review not found for book id: " + id + " -- Review id: " + reviewId);
            review.get().setComment(dto.reviewComment());
            book.setReview(review.get());
        }

        BookModel bookUpdated = this.bookRepository.save(book);
        LOGGER.info("Book updated: {}", bookUpdated);

        return new ServiceResponse<>(HttpStatus.CREATED, bookUpdated);
    }

    @Transactional
    public ServiceResponse<Boolean> delete (UUID id) {
        BookModel book = this.getById(id).body();
        this.bookRepository.delete(book);
        LOGGER.info("Book deleted for id: {}", id);
        return new ServiceResponse<>(HttpStatus.OK, true);
    }

}
