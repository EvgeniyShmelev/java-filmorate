package ru.yandex.practicum.filmorate.utill;

public class ID {
    private static int userId = 0;
    private static int filmId = 0;

    public static synchronized int getUserId() {
        return userId++;
    }

    public static synchronized int getFilmId() {
        return filmId++;
    }
}
