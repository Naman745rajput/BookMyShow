package com.naman.bms.controller;

import com.naman.bms.dto.ShowDto;
import com.naman.bms.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/shows")
public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping
    public ResponseEntity<ShowDto> createShow(@RequestBody ShowDto showDto) {
        return new ResponseEntity<>(showService.createShow(showDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowDto> getShowById(@PathVariable Long id) {
        return ResponseEntity.ok(showService.getShowById(id));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ShowDto>> getShowsByMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(showService.getShowsByMovie(movieId));
    }

    @GetMapping
    public ResponseEntity<List<ShowDto>> getAllShows() {
        return ResponseEntity.ok(showService.getAllShows());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ShowDto>> getShowsByCity(
            @RequestParam Long movieId,
            @RequestParam String city
    ) {
        return ResponseEntity.ok(showService.getShowsByMovieAndCity(movieId, city));
    }

    @GetMapping("/date")
    public ResponseEntity<List<ShowDto>> getShowsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        return ResponseEntity.ok(showService.getShowsByDateRange(startDate, endDate));
    }


}

