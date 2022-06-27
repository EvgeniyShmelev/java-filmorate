package ru.yandex.practicum.filmorate.dao;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exceptions.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Rating;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
public class FilmDbStorageTest {

    @Autowired
    @Qualifier("FilmDbStorage")
    FilmStorage filmStorage;

    @Test
    void addFilmTest() throws ValidationException, FilmAlreadyExistException {
        Film film1 = new Film(1, "Jack Sparrow 1", "pirate 1",
                LocalDate.of(2003, 1, 1), 120, new Rating(1));
        Film resultFilm = filmStorage.add(film1);
        assertEquals(1, resultFilm.getId());
    }

    @Test
    void updateFilmTest() throws ValidationException, FilmAlreadyExistException, FilmNotFoundException {
        Film film1 = new Film(1, "Jack Sparrow 1", "pirate 1",
                LocalDate.of(2003, 1, 1), 120, new Rating(1));
        Film film2 = new Film(1, "Jack Sparrow 1 ver.2", "pirate 2",
                LocalDate.of(2004, 1, 1), 120, new Rating(1));
        filmStorage.add(film1);
        filmStorage.update(film2);
        assertEquals("Jack Sparrow 1 ver.2", filmStorage.getFilmById(1).getName());
    }

    @Test
    void removeUserTest() throws ValidationException, FilmAlreadyExistException, FilmNotFoundException {
        Film film1 = new Film(1, "Jack Sparrow 1", "pirate 1",
                LocalDate.of(2003, 1, 1), 120, new Rating(1));
        Film film2 = new Film(2, "Jack Sparrow 1 ver.2", "pirate 2",
                LocalDate.of(2004, 1, 1), 120, new Rating(1));
        filmStorage.add(film1);
        filmStorage.add(film2);
        filmStorage.remove(2);
        assertEquals(1, filmStorage.getAll().size());
    }

    @Test
    void getAllUserTest() throws ValidationException, FilmAlreadyExistException {
        Film film1 = new Film(1, "Jack Sparrow 1", "pirate 1",
                LocalDate.of(2003, 1, 1), 120, new Rating(1));
        Film film2 = new Film(2, "Jack Sparrow 1 ver.2", "pirate 2",
                LocalDate.of(2004, 1, 1), 120, new Rating(1));
        filmStorage.add(film1);
        filmStorage.add(film2);
        assertEquals(2, filmStorage.getAll().size());
    }

    @Test
    void getUserByIdTest() throws FilmNotFoundException, ValidationException, FilmAlreadyExistException {
        Film film1 = new Film(1, "Jack Sparrow 1", "pirate 1",
                LocalDate.of(2003, 1, 1), 120, new Rating(1));
        Film film2 = new Film(2, "Jack Sparrow 1 ver.2", "pirate 2",
                LocalDate.of(2004, 1, 1), 120, new Rating(1));
        filmStorage.add(film1);
        filmStorage.add(film2);
        assertEquals(1, filmStorage.getFilmById(1).getId());
        assertEquals(2, filmStorage.getFilmById(2).getId());
    }
}
