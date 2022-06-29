package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LikeDbStorage implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;
    private final FilmDbStorage filmDbStorage;

    @Override
    public void add(Integer filmId, Integer userId) {
        jdbcTemplate.update("insert into likes (user_id, film_id) values (?,?)", userId, filmId);
    }

    @Override
    public void remove(Integer filmId, Integer userId) {
        jdbcTemplate.update("delete from likes where user_id = ? and film_id = ?", userId, filmId);
    }

    @Override
    public Collection<Film> getPopular(Integer count) {
        List<Film> filmList = (List<Film>) filmDbStorage.getAll();
        filmList.sort((o1, o2) -> o2.getLikes().size() - o1.getLikes().size());
        return filmList.stream().limit(count).collect(Collectors.toList());
    }
}