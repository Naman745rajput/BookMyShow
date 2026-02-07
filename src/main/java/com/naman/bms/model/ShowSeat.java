package com.naman.bms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shows_seats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="show_id", nullable = false)
    private Show show;

    @ManyToOne
    @JoinColumn(name="seat_id", nullable = false)
    private Seat seat;

    @Column(nullable = false)
    private String status; // e.g., AVAILABLE, BOOKED

    @Column(nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name="booking_id")
    private Booking booking;

    @Version
    private Long version; // For optimistic locking
}
