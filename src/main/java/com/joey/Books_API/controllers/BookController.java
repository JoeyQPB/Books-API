package com.joey.Books_API.controllers;

import com.joey.Books_API.models.BookModel;
import com.joey.Books_API.models.dtos.BookModelDto;
import com.joey.Books_API.models.dtos.BookModelUpdateDto;
import com.joey.Books_API.services.BookService;
import com.joey.Books_API.services.dto.ServiceResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<BookModel> create (@RequestBody @Valid BookModelDto bookModelDto) {
        ServiceResponse<BookModel> serviceResponse = this.bookService.save(bookModelDto);
        return ResponseEntity.status(serviceResponse.httpStatus()).body(serviceResponse.body());
    }

    @GetMapping
    public ResponseEntity<Iterable<BookModel>> getAll () {
        ServiceResponse<Iterable<BookModel>> serviceResponse = this.bookService.getAllBooks();
        return ResponseEntity.status(serviceResponse.httpStatus()).body(serviceResponse.body());
    }

    @GetMapping("?page={page}&size={size}")
    public ResponseEntity<Page<BookModel>> getAllPagination (@PathVariable @Valid int page,
                                                             @PathVariable @Valid int size) {
        ServiceResponse<Page<BookModel>> serviceResponse = this.bookService.getAllPage(page, size);
        return ResponseEntity.status(serviceResponse.httpStatus()).body(serviceResponse.body());
    }

    @GetMapping("/sort?page={page}&size={size}")
    public ResponseEntity<Iterable<BookModel>> getAllPaginationAndSorting (@PathVariable @Valid int page,
                                                                           @PathVariable @Valid int size) {
        ServiceResponse<Page<BookModel>> serviceResponse = this.bookService.getAllPageSortedByName(page, size);
        return ResponseEntity.status(serviceResponse.httpStatus()).body(serviceResponse.body());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookModel> getById (@PathVariable @Valid UUID id) {
        ServiceResponse<BookModel> serviceResponse = this.bookService.getById(id);
        return ResponseEntity.status(serviceResponse.httpStatus()).body(serviceResponse.body());
    }

    @GetMapping("/q_name={name}")
    public ResponseEntity<BookModel> getByName (@PathVariable @Valid String name) {
        ServiceResponse<BookModel> serviceResponse = this.bookService.getByName(name);
        return ResponseEntity.status(serviceResponse.httpStatus()).body(serviceResponse.body());
    }

    @GetMapping("/q_author={author}")
    public ResponseEntity<Set<BookModel>> getByAuthor (@PathVariable @Valid String author) {
        ServiceResponse<Set<BookModel>> serviceResponse = this.bookService.getBooksByAuthorName(author);
        return ResponseEntity.status(serviceResponse.httpStatus()).body(serviceResponse.body());
    }

    @GetMapping("/q_publisher={publisher}")
    public ResponseEntity<Set<BookModel>> getByPublisher (@PathVariable @Valid String publisher) {
        ServiceResponse<Set<BookModel>> serviceResponse = this.bookService.getBooksByPublisher(publisher);
        return ResponseEntity.status(serviceResponse.httpStatus()).body(serviceResponse.body());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookModel> update (@RequestBody @Valid BookModelUpdateDto data,
                                             @PathVariable @Valid UUID id) {
        ServiceResponse<BookModel> serviceResponse = this.bookService.update(id, data);
        return ResponseEntity.status(serviceResponse.httpStatus()).body(serviceResponse.body());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete (@PathVariable @Valid UUID id) {
        ServiceResponse<Boolean> serviceResponse = this.bookService.delete(id);
        return ResponseEntity.status(serviceResponse.httpStatus()).body(serviceResponse.body());
    }
}
