package com.example.homework001.model.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiResponse <T> {
    private Boolean success;
    private String message;
    private HttpStatus httpStatus;
    private T playLoad;
    private LocalDateTime timestamp;
}
