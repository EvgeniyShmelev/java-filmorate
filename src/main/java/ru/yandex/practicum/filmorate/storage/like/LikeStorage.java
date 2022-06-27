package ru.yandex.practicum.filmorate.storage.like;

import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.Collection;

public interface LikeStorage {
    void add(Integer id, Integer userId);

    void remove(Integer filmId, Integer userId);

    Collection<Film> getPopular(Integer count);
}