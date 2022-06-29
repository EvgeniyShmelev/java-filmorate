package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@Service //можно будет получить доступ из контроллера.
public class FilmService {
    private FilmStorage filmStorage;
    private UserStorage userStorage;

    @Autowired
    public FilmService(@Qualifier("UserDbStorage") UserStorage userStorage,
                       @Qualifier("FilmDbStorage") FilmStorage filmStorage){
        this.userStorage = userStorage;
        this.filmStorage = filmStorage;
    }

    public Film add(Film film) throws ValidationException, FilmAlreadyExistException {
        return filmStorage.add(film);
    }

    public Film update(Film film) throws FilmNotFoundException {
        return filmStorage.update(film);
    }

    public Film get(Integer id) throws FilmNotFoundException, EntityNotFoundException {
        return filmStorage.getFilmById(id);
    }

    public void remove(Integer id) throws FilmNotFoundException {
        filmStorage.remove(id);
    }

    public Collection<Film> getAll() {
        return filmStorage.getAll();
    }

    //Добавление лайка фильму
    public void addLike(Integer filmId, Integer userId) throws FilmNotFoundException, UserNotFoundException, EntityNotFoundException {
        filmStorage.addLike(filmStorage.getFilmById(filmId), userStorage.getUserById(userId));
    }

    //Удаление лайка у фильма
    public void removeLike(Integer filmId, Integer userId) throws FilmNotFoundException, UserNotFoundException, EntityNotFoundException {
        filmStorage.removeLike(filmStorage.getFilmById(filmId), userStorage.getUserById(userId));
    }

    //Получение списка наиболее популярных фильмов
    public Collection<Film> getMostPopular(Integer count) {
        return filmStorage.getMostPopular(count);
    }
}
