package com.example.homework001.model.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaginationInfo {
    private Integer totalElements;
    private Integer currentPage;
    private Integer pageSize;
}
