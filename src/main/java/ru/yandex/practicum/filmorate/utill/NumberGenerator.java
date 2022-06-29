package ru.yandex.practicum.filmorate.utill;

public class NumberGenerator {
    private static int userId = 1;
    private static int filmId = 1;

    public static int getUserId() {
        return userId++;
    }

    public static int getFilmId() {
        return filmId++;
    }
}
