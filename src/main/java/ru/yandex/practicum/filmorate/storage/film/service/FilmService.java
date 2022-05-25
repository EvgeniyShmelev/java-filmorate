package ru.yandex.practicum.filmorate.storage.film.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service //можно будет получить доступ из контроллера.
public class FilmService {
    private FilmStorage filmStorage;
    private UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film add(Film film) {
        return filmStorage.add(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public Film get(Integer id) {
        return filmStorage.get(id);
    }

    public void remove(Integer id) {
        filmStorage.remove(id);
    }

    public Collection<Film> getAll() {
        return filmStorage.getAll();
    }

    //Добавление лайка фильму
    public void addLike(Integer filmId, Integer userId) {
        Film film = filmStorage.get(filmId);
        User user = userStorage.getUserById(userId);

        if (film != null && user != null) {
            if (film.getLikes() == null) film.setLikes(new HashSet<>());
            film.getLikes().add(user.getId());
        }
    }

    //Удаление лайка у фильма
    public void removeLike(Integer filmId, Integer userId) {
        Film film = filmStorage.get(filmId);
        User user = userStorage.getUserById(userId);

        if (film != null && user != null) {
            if (film.getLikes() == null) film.setLikes(new HashSet<>());
            film.getLikes().remove(user.getId());
        }
    }

    //Получение списка наиболее популярных фильмов
    public Collection<Film> getMostPopular(Integer count) {
        Collection<Film> films = filmStorage.getAll();
        return films.stream()
                .sorted((film1, film2) -> Integer.compare((film2 == null ||
                                film2.getLikes() == null) ? 0 : film2.getLikes().size(),
                        (film1 == null ||
                                film1.getLikes() == null) ? 0 : film1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
