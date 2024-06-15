package com.joey.Books_API.services;

import com.joey.Books_API.exceptions.ItemNotFoundException;
import com.joey.Books_API.models.PublisherModel;
import com.joey.Books_API.models.dtos.PublisherDto;
import com.joey.Books_API.repositories.PublisherRepository;
import com.joey.Books_API.services.dto.ServiceResponse;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PublisherService {

    private final Logger LOGGER = LoggerFactory.getLogger(PublisherService.class);
    @Autowired
    private PublisherRepository publisherRepository;

    @Transactional
    public ServiceResponse<PublisherModel> save (PublisherDto publisherDto) {
        PublisherModel publisher = new PublisherModel();
        publisher.setName(publisherDto.name());

        PublisherModel publisherCreated = this.publisherRepository.save(publisher);
        LOGGER.info("Publisher Created: {}", publisherCreated);

        return new ServiceResponse<>(HttpStatus.CREATED, publisherCreated);
    }

    public ServiceResponse<PublisherModel> getById (UUID id) {
        PublisherModel publisher = this.publisherRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Publisher not found for id: " + id));

        LOGGER.info("Publisher found: {}", publisher);
        return new ServiceResponse<>(HttpStatus.OK, publisher);
    }

    public ServiceResponse<PublisherModel> getByName (String name) {
        PublisherModel publisher = this.publisherRepository.findByName(name)
                .orElseThrow(() -> new ItemNotFoundException("Publisher not found for name: " + name));

        LOGGER.info("Publisher found: {}", publisher);
        return new ServiceResponse<>(HttpStatus.OK, publisher);
    }

    public ServiceResponse<Iterable<PublisherModel>> getAllPublishers () {
        Iterable<PublisherModel> publisher = this.publisherRepository.findAll();

        LOGGER.info("Publisher found: {}", publisher);
        return new ServiceResponse<>(HttpStatus.OK, publisher);
    }

    @Transactional
    public ServiceResponse<PublisherModel> update (UUID id, PublisherDto publisherDto) {
        PublisherModel publisherUpdated = this.publisherRepository.findById(id)
                .map(publisher -> {
                    publisher.setName(publisherDto.name());
                    return this.publisherRepository.save(publisher);
                })
                .orElseThrow(() -> new ItemNotFoundException("Publisher not found for: " + publisherDto.name()));

        LOGGER.info("Publisher updated: {}", publisherUpdated);
        return new ServiceResponse<>(HttpStatus.OK, publisherUpdated);
    }

    @Transactional
    public ServiceResponse<Boolean> delete (UUID id) {
        PublisherModel publisher = this.getById(id).body();
        this.publisherRepository.delete(publisher);

        LOGGER.info("Publisher deleted: {}", id);
        return new ServiceResponse<>(HttpStatus.OK, true);
    }
}
