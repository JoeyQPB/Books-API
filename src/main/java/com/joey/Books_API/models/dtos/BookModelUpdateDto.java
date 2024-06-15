package com.joey.Books_API.models.dtos;

import java.util.Set;

public record BookModelUpdateDto ( String title,
                                   String publisherName,
                                   Set<String> authorNames,
                                   String reviewComment) {
}
