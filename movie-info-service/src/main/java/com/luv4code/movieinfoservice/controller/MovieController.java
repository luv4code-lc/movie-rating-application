package com.luv4code.movieinfoservice.controller;

import com.luv4code.movieinfoservice.models.Movie;
import com.luv4code.movieinfoservice.models.MovieSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    @Value("${api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @GetMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
        MovieSummary movieSummary = restTemplate
                .getForObject("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey, MovieSummary.class);
        return Movie.builder()
                .movieId(movieId)
                .name(movieSummary.getTitle())
                .description(movieSummary.getOverview())
                .build();
    }
}
