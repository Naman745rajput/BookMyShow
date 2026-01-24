package com.naman.bms.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeatDto {

    private Long id;
    private SeatDto seat;
    private String status; // e.g., AVAILABLE, BOOKED, BLOCKED
    private Double price; // Price for this specific show
}
