package com.joey.Books_API.models.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record BookModelDto (@NotBlank String title,
                            @NotBlank String publisherName,
                            @NotBlank Set<String> authorNames,
                            String reviewComment)  {
}
