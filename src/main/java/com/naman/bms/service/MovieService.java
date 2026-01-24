package com.naman.bms.service;


import com.naman.bms.dto.MovieDto;
import com.naman.bms.exception.ResourceNotFoundException;
import com.naman.bms.model.Movie;
import com.naman.bms.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {


    @Autowired
    private MovieRepository movieRepository;

    public MovieDto createMovie(MovieDto movieDto)
    {
        Movie movie = mapToEntity(movieDto);
        Movie savedMovie = movieRepository.save(movie);
        return mapToDto(savedMovie);
    }

    public MovieDto getMovieById(Long id)
    {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Movie not found with id: "+id));
        return mapToDto(movie);
    }

    public List<MovieDto> getAllMovies()
    {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<MovieDto> getMoviesByLanguage(String language)
    {
        List<Movie> movies = movieRepository.findByLanguage(language);
        return movies.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    public List<MovieDto> getMoviesByGenre(String genre)
    {
        List<Movie> movies = movieRepository.findByGenre(genre);
        return movies.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    public List<MovieDto> searchMovie(String title)
    {
        List<Movie> movies = movieRepository.findByTitleContaining(title);
        return movies.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public MovieDto updateMovie(Long id, MovieDto movieDto)
    {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Movie not found with id: "+id));

        movie.setTitle(movieDto.getTitle());
        movie.setDescription(movieDto.getDescription());
        movie.setLanguage(movieDto.getLanguage());
        movie.setGenre(movieDto.getGenre());
        movie.setDurationMins(movieDto.getDurationMins());
        movie.setReleaseDate(movieDto.getReleaseDate());
        movie.setPosterUrl(movieDto.getPosterUrl());

        Movie updatedMovie = movieRepository.save(movie);
        return mapToDto(updatedMovie);
    }

    public void deleteMovie(Long id)
    {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(()->new
                        ResourceNotFoundException("Movie not found with id: "+id));
        movieRepository.delete(movie);
    }


    private MovieDto mapToDto(Movie movie)
    {
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movie.getId());
        movieDto.setTitle(movie.getTitle());
        movieDto.setDescription(movie.getDescription());
        movieDto.setLanguage(movie.getLanguage());
        movieDto.setGenre(movie.getGenre());
        movieDto.setDurationMins(movie.getDurationMins());
        movieDto.setReleaseDate(movie.getReleaseDate());
        movieDto.setPosterUrl(movie.getPosterUrl());
        return movieDto;
    }



    private Movie mapToEntity(MovieDto movieDto)
    {
        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setDescription(movieDto.getDescription());
        movie.setLanguage(movieDto.getLanguage());
        movie.setGenre(movieDto.getGenre());
        movie.setDurationMins(movieDto.getDurationMins());
        movie.setReleaseDate(movieDto.getReleaseDate());
        movie.setPosterUrl(movieDto.getPosterUrl());
        return movie;
    }


}
