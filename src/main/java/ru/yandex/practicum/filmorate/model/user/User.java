package ru.yandex.practicum.filmorate.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    //целочисленный идентификатор
    private int id;
    //Электронная почта
    //@NotBlank — аннотированный элемент не должен быть null и должен содержать хотя бы один не пробельный символ.
    //@NotEmpty — аннотированный элемент не должен быть null или пустым.
    @Email(message = "Некорректный Email!")
    private String email;
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

    public User(int id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public String getName() {
        //Имя для отображения может быть пустым — в таком случае будет использован логин
        return (name == null || name.isEmpty()) ? login : name;
    }
}
