package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;


import java.time.Duration;
import java.time.LocalDate;

@SpringBootTest
class FilmControllerTest {


    @Test
    void checkValidFilmName() {
        Film film1 = new Film("Jack Sparrow 1", "pirate 1",
                LocalDate.of(2003, 1, 1), Duration.ofMinutes(120));
        Assertions.assertTrue(FilmController.validate(film1));
        Film film2 = new Film(null, "pirate 2",
                LocalDate.of(2004, 1, 1), Duration.ofMinutes(120));
        Assertions.assertFalse(FilmController.validate(film2));
        Film film3 = new Film(" ", "pirate 3",
                LocalDate.of(2005, 1, 1), Duration.ofMinutes(120));
        Assertions.assertFalse(FilmController.validate(film3));
    }

    @Test
    void checkValidFilmDescription() {
        String testDescription = "1".repeat(201);

        Film film1 = new Film("Jack Sparrow 1", "pirate 1",
                LocalDate.of(2003, 1, 1), Duration.ofMinutes(120));
        Assertions.assertTrue(FilmController.validate(film1));
        Film film2 = new Film("Jack Sparrow 2", testDescription,
                LocalDate.of(2004, 1, 1), Duration.ofMinutes(120));
        Assertions.assertFalse(FilmController.validate(film2));
        Film film3 = new Film("Jack Sparrow 3 ", "",
                LocalDate.of(1900, 3, 25), Duration.ofMinutes(200));
        Assertions.assertTrue(FilmController.validate(film3));
    }

    @Test
    void checkValidFilmDateRelease() {
        Film film1 = new Film("Jack Sparrow 1", "pirate 1",
                LocalDate.of(2003, 1, 1), Duration.ofMinutes(120));
        Assertions.assertTrue(FilmController.validate(film1));
        Film film2 = new Film("Jack Sparrow 2", "pirate 2",
                LocalDate.of(1895, 12, 27), Duration.ofMinutes(120));
        Assertions.assertFalse(FilmController.validate(film2));
    }

    @Test
    void checkValidFilmDuration() {
        Film film1 = new Film("Jack Sparrow 1", "pirate 1",
                LocalDate.of(2003, 1, 1), Duration.ofMinutes(120));
        Assertions.assertTrue(FilmController.validate(film1));
        Film film2 = new Film("Jack Sparrow 2", "pirate 2",
                LocalDate.of(2004, 1, 1), Duration.ofMinutes(-120));
        Assertions.assertFalse(FilmController.validate(film2));
    }
}