package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exceptions.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.user.User;

import java.util.Collection;

public interface FilmStorage {
    Film add(Film film) throws FilmAlreadyExistException, ValidationException;

    Film update(Film film) throws FilmNotFoundException;

    void remove(Integer id) throws FilmNotFoundException;

    Film getFilmById(Integer id) throws FilmNotFoundException;

    Collection<Film> getAll();

    //реализация 10 и 11 тз

    //Добавление лайка фильму
    void addLike(Film film, User user);

    //Удаление лайка у фильма
    void removeLike(Film film, User user);

    //Получение списка наиболее популярных фильмов
    Collection<Film> getMostPopular(Integer count);
}
