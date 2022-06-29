package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@Data
public class Film {
    //целочисленный идентификатор
    private int id;
    //название
    @NotBlank(message = "Название фильма не может быть пустым!")
    private final String name;
    //описание
    @Size(min = 1, message = "Нет описания фильма!")
    @Size(max = 200, message = "Описание фильма занимает больше 200 символов!")
    private String description;
    //дата релиза;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    //продолжительность фильма.
    @Positive(message = "Продолжительность фильма не может быть отрицательной!")
    private int duration;
    //лайки пользователей
    private Set<Integer> likes;
}
