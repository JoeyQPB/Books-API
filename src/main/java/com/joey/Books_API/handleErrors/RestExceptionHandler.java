package com.joey.Books_API.handleErrors;

import com.joey.Books_API.exceptions.ItemNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    private ResponseEntity<String> exceptionHandler (Exception exception) {
        LOGGER.error("Error: Exception - " + exception.getMessage());
        RestErrorMessage<String> threatResponse = new RestErrorMessage<>(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(threatResponse.httpStatus()).body("ControllerAdvice - " + threatResponse.body());
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<String> runtimeExceptionHandler (RuntimeException exception) {
        LOGGER.error("Error: RuntimeException - " + exception.getMessage());
        RestErrorMessage<String> threatResponse = new RestErrorMessage<>(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(threatResponse.httpStatus()).body("ControllerAdvice - " + threatResponse.body());
    }

    @ExceptionHandler(NullPointerException.class)
    private ResponseEntity<String> nullPointerExceptionHandler (NullPointerException exception) {
        LOGGER.error("Error: NullPointerException - " + exception.getMessage());
        RestErrorMessage<String> threatResponse = new RestErrorMessage<>(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(threatResponse.httpStatus()).body("ControllerAdvice - " + threatResponse.body());
    }

    @ExceptionHandler(ItemNotFoundException.class)
    private ResponseEntity<String> itemNotFoundException (ItemNotFoundException exception) {
        LOGGER.error("Error: ItemNotFoundException - " + exception.getMessage());
        RestErrorMessage<String> threatResponse = new RestErrorMessage<>(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(threatResponse.httpStatus()).body("ControllerAdvice - " + threatResponse.body());
    }
}
