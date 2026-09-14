package com.mayank.hospitalrecordsscraper.exception;

import com.mayank.hospitalrecordsscraper.dto.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ScraperException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleScraperException(ScraperException e) {

        return new ErrorResponse(
                "Hospital import failed",
                e.getMessage()
        );
    }
}