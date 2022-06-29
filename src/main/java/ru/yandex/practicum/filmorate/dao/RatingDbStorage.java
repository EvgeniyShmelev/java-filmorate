package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.film.Rating;
import ru.yandex.practicum.filmorate.storage.rating.RatingStorage;

import java.util.ArrayList;
import java.util.Collection;

@Component("RatingDbStorage")
@Slf4j
@RequiredArgsConstructor
public class RatingDbStorage implements RatingStorage {
    private final JdbcTemplate jdbcTemplate;


    @Override
    public Collection<Rating> getAllRating() {
        Collection<Rating> ratings = new ArrayList<>();
        SqlRowSet mpaRows = jdbcTemplate.queryForRowSet("select * from rating_mpa");

        while (mpaRows.next()) {
            ratings.add(new Rating(mpaRows.getInt("rating_id"),
                    mpaRows.getString("name")));
        }
        return ratings;
    }

    @Override
    public Rating getRatingById(int id) throws EntityNotFoundException {
        if (id < 1) {
            log.error("Рейтинг {} не найден!", id);
            throw new EntityNotFoundException("Рейтинг с идентификатором " + id + " не найден!");
        }
        SqlRowSet mpaRows = jdbcTemplate.queryForRowSet("select * from rating_mpa WHERE rating_id = ?", id);
        if (mpaRows.next()) {
            Rating rating = new Rating(mpaRows.getInt("rating_id"),
                    mpaRows.getString("name"));
            return rating;
        } else
            log.error("Рейтинг {} не найден!", id);
        throw new EntityNotFoundException("Рейтинг с идентификатором " + id + " не найден!");
    }
}