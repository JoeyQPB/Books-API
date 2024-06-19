package com.joey.Books_API.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record BookModelDto (@NotBlank String title,
                            @NotBlank String publisherName,
                            @NotEmpty Set<String> authorNames,
                            String reviewComment)  {
}
