package ru.yandex.practicum.filmorate.utill;

public class ID {
    private static int userId = 0;
    private static int filmId = 0;

    public static int getUserId() {
        return userId++;
    }

    public static int getFilmId() {
        return filmId++;
    }
}
