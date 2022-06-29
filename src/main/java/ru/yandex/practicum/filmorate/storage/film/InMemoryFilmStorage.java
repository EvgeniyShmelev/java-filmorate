package ru.yandex.practicum.filmorate.storage.film;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.yandex.practicum.filmorate.exceptions.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryGeneralStorage;

import java.time.LocalDate;

@Component // аннотация указывает, что класс нужно добавить в контекст
@Slf4j
public class InMemoryFilmStorage extends InMemoryGeneralStorage<Film> implements FilmStorage {

    public static boolean validate(Film film) {
        final LocalDate releaseDate = LocalDate.of(1895, 12, 28);

        return StringUtils.hasText(film.getName())
                && (film.getDescription().length() <= 200)
                && StringUtils.hasLength(film.getDescription())
                && (film.getDuration() > 0)
                && (film.getReleaseDate().isAfter(releaseDate));
    }

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
    public Film get(Integer id) throws FilmNotFoundException {
        if (general.containsKey(id)) {
            return general.get(id);
        } else {
            throw new FilmNotFoundException("Фильм с id " + id + " не найден!");
        }
    }
}
