package ru.yandex.practicum.filmorate.storage.film;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.InMemoryGeneralStorage;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.utill.FilmValidation.validate;

@Component // аннотация указывает, что класс нужно добавить в контекст
@Slf4j
public class InMemoryFilmStorage extends InMemoryGeneralStorage<Film> implements FilmStorage {


    @Override
    public Film add(Film film) throws FilmAlreadyExistException, ValidationException {
        if (validate(film)) {
            if (general.containsKey(film.getId())) {
                log.error("Фильм с ID #" + film.getId() + " уже существует!");
                throw new FilmAlreadyExistException("Такой фильм уже существует.");
            }
            if (film.getId() <= 0) {
                film.setId(calculateId());
            }
            general.put(film.getId(), film);
            log.info("Добавлен фильм: " + film);
        } else {
            throw new ValidationException("Данные не верны");
        }
        return film;
    }

    @Override
    public Film update(Film film) throws FilmNotFoundException {
        if (general.containsKey(film.getId())) {
            general.put(film.getId(), film);
            log.info("Обновлен фильм: " + film);
            return film;
        } else {
            log.error("film с id " + film.getId() + " не найден!");
            throw new FilmNotFoundException("film с id " + film.getId() + " не найден!");
        }
    }

    @Override
    public void remove(Integer id) throws FilmNotFoundException {
        if (general.containsKey(id)) {
            general.remove(id);
            log.info("Удален " + general.get(id));
        } else {
            throw new FilmNotFoundException("Фильм с id " + id + " не найден!");
        }
    }

    @Override
    public Film getFilmById(Integer id) throws FilmNotFoundException {
        if (general.containsKey(id)) {
            return general.get(id);
        } else {
            throw new FilmNotFoundException("Фильм с id " + id + " не найден!");
        }
    }

    public void addLike(Film film, User user) {
        if (film != null && user != null) {
            if (film.getLikes() == null) film.setLikes(new HashSet<>());
            film.getLikes().add(user.getId());
        }
    }

    public void removeLike(Film film, User user) {
        if (film != null && user != null && film.getLikes().contains(user.getId())) {
            film.getLikes().remove(user.getId());
        }
    }

    public Collection<Film> getMostPopular(Integer count) {
        return getAll().stream()
                .sorted((film1, film2) -> Integer.compare((film2 == null ||
                                film2.getLikes() == null) ? 0 : film2.getLikes().size(),
                        (film1 == null ||
                                film1.getLikes() == null) ? 0 : film1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());

    }
}
