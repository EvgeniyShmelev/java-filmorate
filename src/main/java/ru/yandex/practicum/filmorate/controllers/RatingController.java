package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.film.Rating;
import ru.yandex.practicum.filmorate.service.RatingService;

import java.util.Collection;

@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    //Возвращение рейтинга по идентификатору
    @GetMapping("/{id}")
    public Rating findMpa(@PathVariable int id) throws EntityNotFoundException {
        return ratingService.getRatingById(id);
    }

    @GetMapping
    public Collection<Rating> getAllRating() {
        return ratingService.getAllRating();
    }
}
