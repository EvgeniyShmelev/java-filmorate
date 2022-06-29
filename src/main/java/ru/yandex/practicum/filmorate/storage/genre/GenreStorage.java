package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.film.Genre;

import java.util.Collection;
import java.util.Set;

public interface GenreStorage {

    Set<Genre> getAllGenres();

    Genre getGenreById(int id) throws EntityNotFoundException;
}