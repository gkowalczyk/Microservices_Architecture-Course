package com.example.movieservicemovierecommendationsystem;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MovieNotFoundException.class)
    public ProblemDetail problemDetail(MovieNotFoundException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        problemDetail.setDetail("Move not found");
        problemDetail.setDetail(exception.getMessage());
        return problemDetail;
    }
}
