package com.naman.bms.controller;

import com.naman.bms.dto.SeatDto;
import com.naman.bms.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @PostMapping("/screen/{screenId}")
    public ResponseEntity<SeatDto> createSeat(@PathVariable Long screenId ,@RequestBody SeatDto seatDto) {
        return new ResponseEntity<>(seatService.createSeat(screenId,seatDto), HttpStatus.CREATED);
    }

    @GetMapping("/screen/{screenId}")
    public ResponseEntity<List<SeatDto>> getSeatsByScreen(@PathVariable Long screenId) {
        return ResponseEntity.ok(seatService.getSeatsByScreen(screenId));
    }
}