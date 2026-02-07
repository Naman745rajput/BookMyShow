package com.naman.bms.controller;

import com.naman.bms.dto.ScreenDto;
import com.naman.bms.service.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/screens")
public class ScreenController {

    @Autowired
    private ScreenService screenService;

    @PostMapping("/theater/{theaterId}")
    public ResponseEntity<ScreenDto> createScreen(
            @PathVariable Long theaterId,
            @RequestBody ScreenDto screenDto
    ) {
        return new ResponseEntity<>(
                screenService.createScreen(theaterId, screenDto),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScreenDto> getScreenById(@PathVariable Long id) {
        return ResponseEntity.ok(screenService.getScreenById(id));
    }

    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<List<ScreenDto>> getScreensByTheater(@PathVariable Long theaterId)
    {
        return ResponseEntity.ok(screenService.getScreensByTheater(theaterId));
    }
}
