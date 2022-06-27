package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Rating;
import ru.yandex.practicum.filmorate.utill.FilmValidation;


import java.time.LocalDate;

@SpringBootTest
class FilmControllerTest {

    @Test
    void checkValidFilmName() {
        Film film1 = new Film(1, "Jack Sparrow 1", "pirate 1",
                LocalDate.of(2003, 1, 1), 120, new Rating(1));
        Assertions.assertTrue(FilmValidation.validate(film1));
        Film film2 = new Film(2, null, "pirate 2",
                LocalDate.of(2004, 1, 1), 120, new Rating(1));
        Assertions.assertFalse(FilmValidation.validate(film2));
        Film film3 = new Film(3, " ", "pirate 3",
                LocalDate.of(2005, 1, 1), 120, new Rating(1));
        Assertions.assertFalse(FilmValidation.validate(film3));
    }

    @Test
    void checkValidFilmDescription() {
        String testDescription = "1".repeat(201);

        Film film1 = new Film(4, "Jack Sparrow 1", "pirate 1",
                LocalDate.of(2003, 1, 1), 120, new Rating(1));
        Assertions.assertTrue(FilmValidation.validate(film1));
        Film film2 = new Film(5, "Jack Sparrow 2", testDescription,
                LocalDate.of(2004, 1, 1),120, new Rating(1));
        Assertions.assertFalse(FilmValidation.validate(film2));
        Film film3 = new Film(6, "Jack Sparrow 3 ", "",
                LocalDate.of(1900, 3, 25), 200, new Rating(1));
        Assertions.assertFalse(FilmValidation.validate(film3));
    }

    @Test
    void checkValidFilmDateRelease() {
        Film film1 = new Film(7, "Jack Sparrow 1", "pirate 1",
                LocalDate.of(2003, 1, 1), 120, new Rating(1));
        Assertions.assertTrue(FilmValidation.validate(film1));
        Film film2 = new Film(8, "Jack Sparrow 2", "pirate 2",
                LocalDate.of(1895, 12, 27), 120, new Rating(1));
        Assertions.assertFalse(FilmValidation.validate(film2));
    }

    @Test
    void checkValidFilmDuration() {
        Film film1 = new Film(9, "Jack Sparrow 1", "pirate 1",
                LocalDate.of(2003, 1, 1), 120, new Rating(1));
        Assertions.assertTrue(FilmValidation.validate(film1));
        Film film2 = new Film(10, "Jack Sparrow 2", "pirate 2",
                LocalDate.of(2004, 1, 1),-120, new Rating(1));
        Assertions.assertFalse(FilmValidation.validate(film2));
    }

}