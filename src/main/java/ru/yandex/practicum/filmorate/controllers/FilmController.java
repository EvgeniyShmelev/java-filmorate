package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> getFilms() {
        return new ArrayList<Film>(films.values());
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        if (validate(film)) {
            if (films.containsKey(film.getId())) {
                log.error("Фильм с ID #" + film.getId() + " уже существует!");
                throw new ValidationException("Такой фильм уже существует.");
            }
            log.info("Добавлен фильм: " + film);
            films.put(film.getId(), film);
        } else {
            throw new ValidationException("Данные не верны");
        }
        return film;
    }


    @PutMapping
    public Film update(@RequestBody Film film) {
        if (validate(film)) {
            log.info("Обновлен фильм: " + film);
            films.put(film.getId(), film);
            return film;
        } else {
            throw new ValidationException("Данные не верны");
        }
    }

    public static boolean validate(Film film) {
        final LocalDate RELEASE_DATE = LocalDate.of(1895, 12, 28);

        return !(film.getName() == null)
                && !(film.getName().isBlank())
                && (film.getDescription().length() <= 200)
                && (film.getReleaseDate().isAfter(RELEASE_DATE))
                && (!film.getDuration().isNegative());
    }
}