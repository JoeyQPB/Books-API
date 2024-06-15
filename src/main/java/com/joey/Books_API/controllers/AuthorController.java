package com.joey.Books_API.controllers;

import com.joey.Books_API.models.AuthorModel;
import com.joey.Books_API.models.dtos.AuthorDto;
import com.joey.Books_API.services.AuthorService;
import com.joey.Books_API.services.dto.ServiceResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorModel> create (@RequestBody @Valid AuthorDto authorDto) {
        ServiceResponse<AuthorModel> serviceResponse = this.authorService.save(authorDto);
        return ResponseEntity.status(serviceResponse.httpStatus()).body(serviceResponse.body());
    }

    @GetMapping
    public ResponseEntity<Iterable<AuthorModel>> getAll () {
        ServiceResponse<Iterable<AuthorModel>> serviceResponse = this.authorService.getAllAuthors();
        return ResponseEntity.status(serviceResponse.httpStatus()).body(serviceResponse.body());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorModel> getById (@PathVariable @Valid UUID id) {
        ServiceResponse<AuthorModel> serviceResponse = this.authorService.getById(id);
        return ResponseEntity.status(serviceResponse.httpStatus()).body(serviceResponse.body());
    }

    @GetMapping("/q_name={name}")
    public ResponseEntity<AuthorModel> getByName (@PathVariable @Valid String name) {
        ServiceResponse<AuthorModel> serviceResponse = this.authorService.getByName(name);
        return ResponseEntity.status(serviceResponse.httpStatus()).body(serviceResponse.body());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorModel> update (@RequestBody @Valid AuthorDto data,
                                               @PathVariable @Valid UUID id) {
        ServiceResponse<AuthorModel> serviceResponse = this.authorService.update(id, data);
        return ResponseEntity.status(serviceResponse.httpStatus()).body(serviceResponse.body());
    }

    @PutMapping("/{authorId}/{bookId}")
    public ResponseEntity<AuthorModel> addBookToAuthor (@PathVariable @Valid UUID authorId,
                                                        @PathVariable @Valid UUID bookId) {
        ServiceResponse<AuthorModel> serviceResponse = this.authorService.addBookToAuthor(authorId, bookId);
        return ResponseEntity.status(serviceResponse.httpStatus()).body(serviceResponse.body());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete (@PathVariable @Valid UUID id) {
        ServiceResponse<Boolean> serviceResponse = this.authorService.delete(id);
        return ResponseEntity.status(serviceResponse.httpStatus()).body(serviceResponse.body());
    }
}
