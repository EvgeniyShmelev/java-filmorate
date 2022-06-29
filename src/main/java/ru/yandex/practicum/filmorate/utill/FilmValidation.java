package ru.yandex.practicum.filmorate.utill;

import org.springframework.util.StringUtils;
import ru.yandex.practicum.filmorate.model.film.Film;

import java.time.LocalDate;

public class FilmValidation {
    public static boolean validate(Film film) {
        LocalDate releaseDate = LocalDate.of(1895, 12, 28);
        return StringUtils.hasText(film.getName())
                && (film.getDescription().length() <= 200)
                && StringUtils.hasLength(film.getDescription())
                && (film.getDuration() > 0)
                && (film.getReleaseDate().isAfter(releaseDate))
                && !(film.getMpa() == null);
    }
}
