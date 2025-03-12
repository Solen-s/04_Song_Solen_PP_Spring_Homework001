package com.example.homework001.controller;

import com.example.homework001.model.api.response.ApiResponse;
import com.example.homework001.model.api.response.ApiResponsePagination;
import com.example.homework001.model.emun.TicketStatus;
import com.example.homework001.model.entity.Ticket;
import com.example.homework001.model.pagination.PageResponseListTicket;
import com.example.homework001.model.pagination.PaginationInfo;
import com.example.homework001.model.request.TicketRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("api/v1/tickets")
public class TicketController {
    private final static List<Ticket> TICKETS = new ArrayList<>();
    private final static AtomicLong ATOMIC_LONG = new AtomicLong(7L);
    LocalDate date = LocalDate.parse("2025-01-01");

    public TicketController() {
        TICKETS.add(new Ticket(1L,"Tina",date,"PP","KPS",15.0,false,TicketStatus.BOOKED, "01"));
        TICKETS.add(new Ticket(2L,"Michel",date,"BTB","Krate",18.5,false, TicketStatus.BOOKED, "04"));
        TICKETS.add(new Ticket(3L,"Michel",date,"BTB","SR",18.5,false, TicketStatus.BOOKED, "04"));
        TICKETS.add(new Ticket(4L,"Michel",date,"KPS","Krate",18.5,false, TicketStatus.BOOKED, "04"));
        TICKETS.add(new Ticket(5L,"Michel",date,"KPC","PP",18.5,false, TicketStatus.BOOKED, "04"));
        TICKETS.add(new Ticket(6L,"Michel",date,"PVH","PP",18.5,false, TicketStatus.BOOKED, "04"));
    }

    //Get All ticket
    @GetMapping
    public ResponseEntity<ApiResponse<List<Ticket>>> getTickets() {
        ApiResponse<List<Ticket>> reponse = new ApiResponse<>(
                true,
                "Get ticket successfully",
                HttpStatus.OK,
                TICKETS,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.OK).body(reponse);
    }


    //Create Ticket
    @PostMapping
    public ResponseEntity<ApiResponse<Ticket>> setTickets(@RequestBody TicketRequest request) {
        Ticket ticket = new Ticket(ATOMIC_LONG.getAndIncrement(), request.getPassengerName(), request.getTravelDate(), request.getSourceStation(), request.getDestinationStation(),request.getPrice(), request.getPaymentStatus(), request.getTicketStatus(),request.getSeatNumber());
        TICKETS.add(ticket);
        ApiResponse<Ticket> response = new ApiResponse<>(
                true,
                "Creating Ticket Successfully",
                HttpStatus.CREATED,
                ticket,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //Search ticket by id
    @GetMapping("/{ticket-id}")
    public ResponseEntity<ApiResponse<Ticket>> getTicketById (@PathVariable ("ticket-id") Long ticketId) {
        for(Ticket ticket : TICKETS) {
            if(ticket.getTicketId().equals(ticketId)){
                ApiResponse<Ticket> response = new ApiResponse<>(
                        true,
                        "Ticket found with id "+ticketId+" successfully" ,
                        HttpStatus.FOUND,
                        ticket,
                        LocalDateTime.now()

                );
                return ResponseEntity.status(HttpStatus.FOUND).body(response);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                false,
                "Ticket not found with id "+ticketId+" !" ,
                HttpStatus.NOT_FOUND,
                null,
                LocalDateTime.now()
        ));
    }

    //Search ticket by name
    @GetMapping("/ticket-search-name")
    public ResponseEntity<ApiResponse<List<Ticket>>> getTicketByName(@RequestParam ("name") String ticketName) {
        for(Ticket ticket : TICKETS) {
            if(ticket.getPassengerName().contains(ticketName)){
                ApiResponse<List<Ticket>> response = new ApiResponse<>(
                          true,
                        "Ticket found with name "+ticketName +" successfully" ,
                        HttpStatus.FOUND,
                        TICKETS,
                        LocalDateTime.now()

                );
                return ResponseEntity.status(HttpStatus.FOUND).body(response);
            }
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new
                ApiResponse<>(
                false,
                "Ticket not found with name "+ ticketName+" !",
                HttpStatus.NOT_FOUND,
                null,
                LocalDateTime.now()
        ));
    }

    //Update ticket by ID
    @PutMapping("/{ticket-id}")
    public ResponseEntity<ApiResponse<Ticket>> updateTicketByID(@PathVariable("ticket-id") Long ticketId,
                               @RequestBody TicketRequest request) {
        for (Ticket ticket : TICKETS){
            if(ticket.getTicketId().equals(ticketId)){
                ticket.setPassengerName(request.getPassengerName());
                ticket.setTravelDate(request.getTravelDate());
                ticket.setSourceStation(request.getSourceStation());
                ticket.setDestinationStation(request.getDestinationStation());
                ticket.setPrice(request.getPrice());
                ticket.setPaymentStatus(request.getPaymentStatus());
                ticket.setTicketStatus(request.getTicketStatus());
                ticket.setSeatNumber(request.getSeatNumber());
                ApiResponse<Ticket> response = new ApiResponse<>(
                        true,
                        "Update ticket ID "+ticketId +"successfully",
                        HttpStatus.OK,
                        ticket,
                        LocalDateTime.now()
                );
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                false,
                "ID " + ticketId + " not available",
                HttpStatus.NOT_FOUND,
                null,
                LocalDateTime.now()
        ));
    }

    //Delete ticket by ID
    @DeleteMapping("/{ticket-id}")
    public ResponseEntity<ApiResponse<String>> deleteTicket(@PathVariable("ticket-id") Long ticketId) {
        for (Ticket ticket : TICKETS) {
            if(ticket.getTicketId().equals(ticketId)){
                TICKETS.remove(ticket);
                ApiResponse<String> response = new ApiResponse<>(
                        true,
                        "Deleted ID " + ticketId +" successfully",
                        HttpStatus.OK,
                        null,
                        LocalDateTime.now()
                );
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }
        return  ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                false,
                "ID "+ ticketId+" not found to deleted",
                HttpStatus.NOT_FOUND,
                null,
                LocalDateTime.now()
        ));
    }

    //filter ticker by ticket status and Travel date
    @GetMapping("/filter")
    @ResponseBody
    public ResponseEntity<ApiResponse<List<Ticket>>> filterTicketByStatusAndTravel(@RequestParam TicketStatus TicketStatus, LocalDate TravelDate) {
        for(Ticket ticket : TICKETS) {
            if(ticket.getTicketStatus().equals(TicketStatus) && ticket.getTravelDate().equals(TravelDate));{
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(
                        true,
                        "ok",
                        HttpStatus.FOUND,
                        TICKETS,
                        LocalDateTime.now()
                ));
            }

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                false,
                "Not found",
                HttpStatus.NOT_FOUND,
                null,
                LocalDateTime.now()

        ));
    }


}
