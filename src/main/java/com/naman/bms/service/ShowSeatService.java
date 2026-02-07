package com.naman.bms.service;

import com.naman.bms.dto.SeatDto;
import com.naman.bms.dto.ShowSeatDto;
import com.naman.bms.exception.ResourceNotFoundException;
import com.naman.bms.model.Screen;
import com.naman.bms.model.Seat;
import com.naman.bms.model.Show;
import com.naman.bms.model.ShowSeat;
import com.naman.bms.repository.ScreenRepository;
import com.naman.bms.repository.ShowRepository;
import com.naman.bms.repository.ShowSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowSeatService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;


    public void initializeShowSeats(Long showId) {

        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show not found"));

        Screen screen = show.getScreen();

        List<Seat> seats = screen.getSeats();

        List<ShowSeat> showSeats = seats.stream()
                .map(seat -> {
                    ShowSeat showSeat = new ShowSeat();
                    showSeat.setShow(show);
                    showSeat.setSeat(seat);
                    showSeat.setStatus("AVAILABLE");
                    showSeat.setPrice(seat.getBasePrice());
                    return showSeat;
                })
                .collect(Collectors.toList());

        showSeatRepository.saveAll(showSeats);
    }

    /**
     * Get all AVAILABLE seats for a show
     */
    public List<ShowSeatDto> getAvailableSeats(Long showId) {
        List<ShowSeat> showSeats = showSeatRepository.findByShowIdAndStatus(showId, "AVAILABLE");

        return showSeats.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ShowSeatDto mapToDto(ShowSeat showSeat) {
        ShowSeatDto dto = new ShowSeatDto();
        dto.setId(showSeat.getId());
        dto.setStatus(showSeat.getStatus());
        dto.setPrice(showSeat.getPrice());

        SeatDto seatDto = new SeatDto();
        seatDto.setId(showSeat.getSeat().getId());
        seatDto.setSeatNumber(showSeat.getSeat().getSeatNumber());
        seatDto.setSeatType(showSeat.getSeat().getSeatType());
        seatDto.setBasePrice(showSeat.getSeat().getBasePrice());

        dto.setSeat(seatDto);
        return dto;
    }

    /**
     * Lock seats temporarily (used during booking)
     */
    public void lockSeats(List<ShowSeat> seats) {
        seats.forEach(seat -> seat.setStatus("LOCKED"));
        showSeatRepository.saveAll(seats);
    }

    /**
     * Release seats (used on cancel / failure)
     */
    public void releaseSeats(List<ShowSeat> seats) {
        seats.forEach(seat -> {
            seat.setStatus("AVAILABLE");
            seat.setBooking(null);
        });
        showSeatRepository.saveAll(seats);
    }
}
