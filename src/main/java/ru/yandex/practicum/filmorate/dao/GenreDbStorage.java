package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.HashSet;
import java.util.Set;

@Component("GenreDbStorage")
@Slf4j
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Set<Genre> getAllGenres() {
        Set<Genre> genres = new HashSet<>();
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet("select * from genre");

        while (genreRows.next()) {
            genres.add(new Genre(genreRows.getInt("genre_id"),
                    genreRows.getString("name")));
        }
        return genres;
    }

    @Override
    public Genre getGenreById(int id) throws EntityNotFoundException {
        if (id < 1) {
            log.error("Жанр с идентификатором " + id + " не найден!");
            throw new EntityNotFoundException("Жанр с идентификатором " + id + " не найден!");
        }
        SqlRowSet mpaRows = jdbcTemplate.queryForRowSet("select * from genre WHERE genre_id = ?", id);
        if (mpaRows.next()) {
            Genre genre = new Genre(mpaRows.getInt("genre_id"),
                    mpaRows.getString("name"));
            return genre;
        } else
            log.error("Жанр с идентификатором " + id + " не найден!");
        throw new EntityNotFoundException("Жанр с идентификатором " + id + " не найден!");
    }
}