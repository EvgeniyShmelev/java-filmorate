package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> users() {
        return new ArrayList<User>(users.values());
    }

    @PostMapping
    public User create(@RequestBody User user) throws ValidationException {
        if (validate(user)) {
            log.info("Добавлен пользователь: " + user);
            users.put(user.getId(), user);
        } else {
            throw new ValidationException("Данные не верны");
        }
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) throws ValidationException {
        if (validate(user)) {
            log.info("Обновлены данные " + user);
            users.put(user.getId(), user);
            return user;
        } else {
            throw new ValidationException("Данные не верны");
        }
    }

    public static boolean validate(User user) {

        return !(user.getEmail().isBlank())
                && !(user.getLogin() == null)
                && !(user.getLogin().isBlank())
                && !(user.getLogin().contains(" "))
                && Pattern.compile("(.+@.+\\..+)").matcher(user.getEmail()).matches()
                && (user.getBirthday().isBefore(LocalDate.now()));
    }
}