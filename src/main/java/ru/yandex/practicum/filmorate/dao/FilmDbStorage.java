package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.Rating;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.utill.FilmValidation;
import ru.yandex.practicum.filmorate.utill.NumberGenerator;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


//Класс DAO — объект доступа к данным.
//Реализация для базы данных
@Component("FilmDbStorage")
@Repository
@Slf4j
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film add(Film film) {
        if (!FilmValidation.validate(film)) {
            throw new ValidationException("данные не верны");
        }
        film.setId(NumberGenerator.getFilmId());
        jdbcTemplate.update("insert into film (film_id, name, description, release_date, duration, rating_id)" +
                        " values (?,?,?,?,?,?)",
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId());


        Set<Genre> genres = film.getGenres();
        if (film.getGenres() != null) {
            for (Genre genre : genres) {
                if (genre.getId() != 0) {
                    jdbcTemplate.update("insert into film_genre (film_id, genre_id) values (?, ?)",
                            film.getId(), genre.getId());
                }
            }
            Set<Genre> allGenres = getFilmGenres(film.getId());
            film.setGenres(allGenres == null ? new HashSet<>() : allGenres);
        }
        log.info("Добавлен фильм: {}", film);
        return film;
    }

    @Override
    public Film update(Film film) throws FilmNotFoundException {
        int id = film.getId();
        if (film.getId() < 1) {
            log.error("Фильм с id {} не найден!", film.getId());
            throw new FilmNotFoundException("Фильм с id " + id + " не найден!");

        }
        String sqlQuery2 = "delete from film_genre where film_id = ?";
        jdbcTemplate.update(sqlQuery2, film.getId());


        String sql = "update film set name = ?, " +
                "description = ?, " +
                "release_date = ?, " +
                "duration = ?, " +
                "rating_id = ?";

        jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId());


        Set<Genre> genres = film.getGenres();
        if (film.getGenres() != null) {
            for (Genre genre : genres) {
                if (genre.getId() != 0) {
                    jdbcTemplate.update("insert into film_genre (film_id, genre_id) values (?, ?)",
                            film.getId(), genre.getId());
                }
            }
            Set<Genre> allGenres = getFilmGenres(film.getId());
            film.setGenres(allGenres == null ? new HashSet<>() : allGenres);
        }

        log.info("Обновлён фильм: {}", film);
        return film;
    }

    @Override
    public void remove(Integer id) {
        jdbcTemplate.update("delete from film where film_id = ?", id);
        log.info("Удалён фильм: {}", id);
    }

    @Override
    public Film getFilmById(Integer filmId) throws FilmNotFoundException {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("select * from v_film_rating_name where film_id = ?", filmId);
        if (filmRows.next()) {
            Film film = new Film(
                    filmRows.getInt("film_id"),
                    filmRows.getString("name"),
                    filmRows.getString("description"),
                    filmRows.getDate("release_date").toLocalDate(),
                    filmRows.getInt("duration"),
                    new Rating(filmRows.getInt("rating_id"),
                            filmRows.getString("rating_name")));

            Set<Genre> genres = getFilmGenres(filmId);
            film.setGenres((genres.size() == 0) ? null : genres);

            return film;
        } else {
            log.error("Не найден фильм: {}", filmId);
            throw new FilmNotFoundException("Фильм с идентификатором " + filmId + " не найден!");
        }
    }

    @Override
    public Collection<Film> getAll() {
        Collection<Film> films = new ArrayList<>();
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("select * from film");

        while (filmRows.next()) {
            films.add(new Film(filmRows.getInt("film_id"),
                    filmRows.getString("name"),
                    filmRows.getString("description"),
                    filmRows.getDate("release_date").toLocalDate(),
                    filmRows.getInt("duration"),
                    new Rating(filmRows.getInt("rating_id"))));
        }
        return films;
    }

    public void addLike(Film film, User user) {
        jdbcTemplate.update(
                "insert into likes (user_id, film_id) values (?,?)",
                user.getId(), film.getId());
        log.info("Пользователь {} поставил лайк к фильму {}",user.getName(),film.getName());
    }

    public void removeLike(Film film, User user) {
        jdbcTemplate.update(
                "delete from likes where user_id = ? and film_id = ?",
                user.getId(), film.getId());
        log.info("Пользователь {} удалил свой лайк к фильму {}",user.getName(),film.getName());
    }

    public Collection<Film> getMostPopular(Integer count) {
        Collection<Film> popularFilm = new ArrayList<>();
        SqlRowSet filmRows;
        String SQL = "select * from film f order by (select count(*) from likes l where l.film_id = f.film_id) desc";

        if (count == null || count == 0)
            filmRows = jdbcTemplate.queryForRowSet(SQL);
        else
            filmRows = jdbcTemplate.queryForRowSet(SQL + " limit ?", count);

        while (filmRows.next()) {
            popularFilm.add(new Film(filmRows.getInt("film_id"),
                    filmRows.getString("name"),
                    filmRows.getString("description"),
                    filmRows.getDate("release_date").toLocalDate(),
                    filmRows.getInt("duration"),
                    new Rating(filmRows.getInt("rating_id"))));
        }
        return popularFilm;
    }

    //Получение списка жанров фильма
    public Set<Genre> getFilmGenres(Integer filmId) {
        Set<Genre> genres = new HashSet<>();
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet("select g.* from film_genre fg " +
                "JOIN genre g on fg.genre_id = g.genre_id where film_id = ? ORDER BY genre_id", filmId);

        while (genreRows.next()) {
            genres.add(new Genre(genreRows.getInt("genre_id"),
                    genreRows.getString("name")));
        }
        return genres;
    }
}
