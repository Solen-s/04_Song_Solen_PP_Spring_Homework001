package com.example.homework001.model.pagination;

import com.example.homework001.model.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageResponseListTicket  {
    private Ticket ticket;
    private PaginationInfo paginationInfo;
}
