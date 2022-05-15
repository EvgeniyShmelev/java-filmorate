package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.utill.ID;

import java.time.LocalDate;

@Data
public class User {
    //целочисленный идентификатор
    private int id;
    //Электронная почта
    //@NotBlank(message = "Электронная почта не может быть пустой!")
    //@NotBlank — аннотированный элемент не должен быть null и должен содержать хотя бы один не пробельный символ.
    //@NotEmpty — аннотированный элемент не должен быть null или пустым.
    //@Email(message = "Некорректный Email!")
    private final String email;
    //логин пользователя
    //@NotBlank(message = "Логин не может быть пустым!")
    private String login;
    //имя для отображения
    private String name;
    //дата рождения
    //@Past(message = "Дата рождения не может быть в будущем!")
    private LocalDate birthday;

    public String getName() {
        //Имя для отображения может быть пустым — в таком случае будет использован логин
        return (name == null || name.isEmpty()) ? login : name;
    }

    public User(String email, String login, String name, LocalDate birthday) {
        this.id = ID.getUserId();
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}
