package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.film.Rating;
import ru.yandex.practicum.filmorate.storage.rating.RatingStorage;

import java.util.Collection;

@Service
public class RatingService {

    private final RatingStorage ratingStorage;
    @Autowired
    public RatingService(@Qualifier("RatingDbStorage") RatingStorage ratingStorage) {
        this.ratingStorage = ratingStorage;
    }

    public Collection<Rating> getAllRating() {
        return ratingStorage.getAllRating();
    }

    public Rating getRatingById(int id) throws EntityNotFoundException {
        return ratingStorage.getRatingById(id);
    }
}