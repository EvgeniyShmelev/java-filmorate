package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.utill.ID;

import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {
    //целочисленный идентификатор
    private int id;
    //название
    //@NotBlank(message = "Название фильма не может быть пустым!")
    private final String name;
    //описание
    //@Size(max = 200,message = "Описание фильма занимает больше 200 символов!")
    private String description;
    //дата релиза;
    private LocalDate releaseDate;
    //продолжительность фильма.
    //@Positive(message = "Продолжительность фильма не может быть отрицательной!")
    private Duration duration;

    public Film(String name, String description, LocalDate releaseDate, Duration duration) {
        this.id = ID.getFilmId();
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}
