package com.joey.Books_API.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record PublisherDto (@NotBlank String name) {
}
