package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exceptions.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Film add(Film film) throws FilmAlreadyExistException, ValidationException;

    Film update(Film film) throws FilmNotFoundException;

    void remove(Integer id) throws FilmNotFoundException;

    Film get(Integer id) throws FilmNotFoundException;

    Collection<Film> getAll();
}
