package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.filmorate.utill.ID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;

@AllArgsConstructor
@Data
public class Film {
    //целочисленный идентификатор
    private int id;
    //название
    @NotBlank(message = "Название фильма не может быть пустым!")
    @NotNull(message = "Название фильма не может быть null!")
    private final String name;
    //описание
    @Size(min = 1, message = "Нет описания фильма!")
    @Size(max = 200, message = "Описание фильма занимает больше 200 символов!")
    private String description;
    //дата релиза;
    private LocalDate releaseDate;
    //продолжительность фильма.
    @Positive(message = "Продолжительность фильма не может быть отрицательной!")
    private Duration duration;

    public int getId() {
        return id = ID.getFilmId();
    }
}
