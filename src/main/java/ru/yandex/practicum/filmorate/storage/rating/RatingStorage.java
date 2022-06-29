package ru.yandex.practicum.filmorate.storage.rating;

import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.film.Rating;

import java.util.Collection;

public interface RatingStorage {

    Collection<Rating> getAllRating();

    Rating getRatingById(int id) throws EntityNotFoundException;
}