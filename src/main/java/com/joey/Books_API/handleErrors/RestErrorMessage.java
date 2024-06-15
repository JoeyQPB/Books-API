package com.joey.Books_API.handleErrors;

import org.springframework.http.HttpStatus;

public record RestErrorMessage<E> (HttpStatus httpStatus, E body) {
}
