package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@Data
public class User {
    //целочисленный идентификатор
    private int id;
    //Электронная почта
    //@NotBlank — аннотированный элемент не должен быть null и должен содержать хотя бы один не пробельный символ.
    //@NotEmpty — аннотированный элемент не должен быть null или пустым.
    @Email(message = "Некорректный Email!")
    private final String email;
    //логин пользователя
    @NotBlank(message = "Логин не может быть пустым!")
    private String login;
    //имя для отображения
    private String name;
    //дата рождения
    @Past(message = "Дата рождения не может быть в будущем!")
    private LocalDate birthday;
    //Друзья пользователя
    private Set<Integer> friends;

    public String getName() {
        //Имя для отображения может быть пустым — в таком случае будет использован логин
        return (name == null || name.isEmpty()) ? login : name;
    }
}
