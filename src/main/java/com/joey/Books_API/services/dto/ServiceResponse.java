package com.joey.Books_API.services.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;

public record ServiceResponse<E> (@NotBlank @Positive HttpStatus httpStatus,
                                     @NotBlank E body){
}
