package com.naman.bms.controller;

import com.naman.bms.dto.ShowSeatDto;
import com.naman.bms.service.ShowSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/show-seats")
public class ShowSeatController {

    @Autowired
    private ShowSeatService showSeatService;

    @GetMapping("/show/{showId}")
    public ResponseEntity<List<ShowSeatDto>> getShowSeats(@PathVariable Long showId) {
        return ResponseEntity.ok(showSeatService.getAvailableSeats(showId));
    }
}

