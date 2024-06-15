package com.joey.Books_API.controllers;

import com.joey.Books_API.models.PublisherModel;
import com.joey.Books_API.models.dtos.PublisherDto;
import com.joey.Books_API.services.PublisherService;
import com.joey.Books_API.services.dto.ServiceResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/publisher")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @PostMapping
    public ResponseEntity<PublisherModel> create (@RequestBody @Valid PublisherDto publisherDto) {
        ServiceResponse<PublisherModel> serviceResponse = this.publisherService.save(publisherDto);
        return ResponseEntity.status(serviceResponse.httpStatus()).body(serviceResponse.body());
    }

    @GetMapping
    public ResponseEntity<Iterable<PublisherModel>> getAll () {
        ServiceResponse<Iterable<PublisherModel>> serviceResponse = this.publisherService.getAllPublishers();
        return ResponseEntity.status(serviceResponse.httpStatus()).body(serviceResponse.body());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherModel> getById (@PathVariable @Valid UUID id) {
        ServiceResponse<PublisherModel> serviceResponse = this.publisherService.getById(id);
        return ResponseEntity.status(serviceResponse.httpStatus()).body(serviceResponse.body());
    }

    @GetMapping("/q_name={name}")
    public ResponseEntity<PublisherModel> getByName (@PathVariable @Valid String name) {
        ServiceResponse<PublisherModel> serviceResponse = this.publisherService.getByName(name);
        return ResponseEntity.status(serviceResponse.httpStatus()).body(serviceResponse.body());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherModel> update (@RequestBody @Valid PublisherDto data,
                                                  @PathVariable @Valid UUID id) {
        ServiceResponse<PublisherModel> serviceResponse = this.publisherService.update(id, data);
        return ResponseEntity.status(serviceResponse.httpStatus()).body(serviceResponse.body());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete (@PathVariable @Valid UUID id) {
        ServiceResponse<Boolean> serviceResponse = this.publisherService.delete(id);
        return ResponseEntity.status(serviceResponse.httpStatus()).body(serviceResponse.body());
    }
}
