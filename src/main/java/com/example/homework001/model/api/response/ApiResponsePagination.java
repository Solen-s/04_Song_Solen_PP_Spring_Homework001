package com.example.homework001.model.api.response;

import com.example.homework001.model.pagination.PageResponseListTicket;
import com.example.homework001.model.pagination.PaginationInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiResponsePagination<T> {
    private Boolean success;
    private String message;
    private HttpStatus httpStatus;
    private PageResponseListTicket playLoad;
    private LocalDateTime timestamp;
}
