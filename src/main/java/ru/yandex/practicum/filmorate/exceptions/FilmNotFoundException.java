package ru.yandex.practicum.filmorate.exceptions;

public class FilmNotFoundException extends Exception {
    public FilmNotFoundException() {
    }

    public FilmNotFoundException(final String message) {
        super(message);
    }
}
