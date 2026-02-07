package com.naman.bms.service;

import com.naman.bms.dto.ScreenDto;
import com.naman.bms.dto.TheaterDto;
import com.naman.bms.exception.ResourceNotFoundException;
import com.naman.bms.model.Screen;
import com.naman.bms.model.Theater;
import com.naman.bms.repository.ScreenRepository;
import com.naman.bms.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScreenService {

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    public ScreenDto createScreen(Long theaterId, ScreenDto screenDto) {

        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Theater not found with id: " + theaterId));

        Screen screen = new Screen();
        screen.setName(screenDto.getName());
        screen.setTotalSeats(screenDto.getTotalSeats());
        screen.setTheater(theater);

        Screen savedScreen = screenRepository.save(screen);
        return mapToDto(savedScreen);
    }

    public ScreenDto getScreenById(Long id) {

        Screen screen = screenRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Screen not found with id: " + id));

        return mapToDto(screen);
    }

    public List<ScreenDto> getScreensByTheater(Long theaterId) {

        List<Screen> screens = screenRepository.findByTheaterId(theaterId);

        return screens.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ScreenDto mapToDto(Screen screen) {

        Theater theater = screen.getTheater();

        TheaterDto theaterDto = new TheaterDto(
                theater.getId(),
                theater.getName(),
                theater.getAddress(),
                theater.getCity(),
                theater.getTotalScreens()
        );

        return new ScreenDto(
                screen.getId(),
                screen.getName(),
                screen.getTotalSeats(),
                theaterDto
        );
    }
}
