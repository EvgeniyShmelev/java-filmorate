package ru.yandex.practicum.filmorate.exceptions;

/*Пользователь ввел некорректные данные - вполне себе штатная ситуация,
  пусть тут будет проверяемое исключение*/
public class ValidationException extends Exception {
    public ValidationException() {
    }

    public ValidationException(final String message) {
        super(message);
    }
}