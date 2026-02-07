package com.naman.bms.service;

import com.naman.bms.dto.SeatDto;
import com.naman.bms.exception.ResourceNotFoundException;
import com.naman.bms.model.Screen;
import com.naman.bms.model.Seat;
import com.naman.bms.repository.ScreenRepository;
import com.naman.bms.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ScreenRepository screenRepository;

    /**
     * Create a seat for a screen (ADMIN use)
     */
    public SeatDto createSeat(Long screenId, SeatDto seatDto) {

        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found"));

        Seat seat = new Seat();
        seat.setSeatNumber(seatDto.getSeatNumber());
        seat.setSeatType(seatDto.getSeatType());
        seat.setBasePrice(seatDto.getBasePrice());
        seat.setScreen(screen);

        Seat savedSeat = seatRepository.save(seat);
        return mapToDto(savedSeat);
    }

    /**
     * Get all seats of a screen (seat layout)
     */
    public List<SeatDto> getSeatsByScreen(Long screenId) {

        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() -> new ResourceNotFoundException("Screen not found"));

        List<Seat> seats = seatRepository.findByScreenId(screenId);

        return seats.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Get seat by id (optional helper)
     */
    public SeatDto getSeatById(Long seatId) {

        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new ResourceNotFoundException("Seat not found"));

        return mapToDto(seat);
    }

    /**
     * Mapping helper
     */
    private SeatDto mapToDto(Seat seat) {

        SeatDto seatDto = new SeatDto();
        seatDto.setId(seat.getId());
        seatDto.setSeatNumber(seat.getSeatNumber());
        seatDto.setSeatType(seat.getSeatType());
        seatDto.setBasePrice(seat.getBasePrice());
        return seatDto;
    }
}
