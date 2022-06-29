package ru.yandex.practicum.filmorate.model.film;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Film {
    //целочисленный идентификатор
    private int id;
    //название
    @NotBlank(message = "Название фильма не может быть пустым!")
    private String name;
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

    //рейтинг МРА
    private Rating mpa;

    //Жанры фильма
    private Set<Genre> genres;

    public Film(int id, String name, String description, LocalDate releaseDate, int duration, Rating mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
    }
}
