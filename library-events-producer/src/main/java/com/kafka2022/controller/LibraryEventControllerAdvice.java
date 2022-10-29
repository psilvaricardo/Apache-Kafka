package com.kafka2022.controller;

import com.kafka2022.controller.errors.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice // provides service to controller component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE) // this provides hierarchy for handling exceptions from the Controller
public class LibraryEventControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<FieldError> errorList = ex.getBindingResult().getFieldErrors();
        String errorMessage = errorList.stream()
                .map(fieldError -> fieldError.getField() + " - " + fieldError.getDefaultMessage())
                .sorted()
                .collect(Collectors.joining(", "));
        log.info("errorMessage : {} ", errorMessage);

        // ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST);
        // response.setMessage(errorMessage);
        // return buildResponseEntity(response);

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse){
        return new ResponseEntity<Object>(errorResponse, errorResponse.getStatus());
    }

}
