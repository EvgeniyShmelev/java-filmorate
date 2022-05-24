package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.HashSet;

@SpringBootTest
class UserControllerTest {

    @Test
    void checkValidUserLogin() {
        User Evgen = new User(1, "jeka@mail.ru", "login1", "Evgen",
                LocalDate.of(2008, 5, 14), new HashSet<>());
        Assertions.assertTrue(InMemoryUserStorage.validate(Evgen));
        User Alex = new User(2, "Alex@mail.ru", null, "Alex",
                LocalDate.of(2008, 5, 13), new HashSet<>());
        Assertions.assertFalse(InMemoryUserStorage.validate(Alex));
        User Igor = new User(3, "Igor@mail.ru", " ", "Igor",
                LocalDate.of(2008, 5, 12), new HashSet<>());
        Assertions.assertFalse(InMemoryUserStorage.validate(Igor));
    }

    @Test
    void checkValidUserEmail() {
        User Evgen = new User(4, "jeka@mail.ru", "login1", "Evgen",
                LocalDate.of(2008, 5, 14), new HashSet<>());
        Assertions.assertTrue(InMemoryUserStorage.validate(Evgen));
        User Alex = new User(4, "Alexmail.ru", "login2", "Alex",
                LocalDate.of(2008, 5, 13), new HashSet<>());
        Assertions.assertFalse(InMemoryUserStorage.validate(Alex));
    }

    @Test
    void checkValidUserName() {
        User Evgen = new User(4, "jeka@mail.ru", "login1", "Evgen",
                LocalDate.of(2008, 5, 14), new HashSet<>());
        Assertions.assertTrue(InMemoryUserStorage.validate(Evgen));
        User Alex = new User(5, "Alex@mail.ru", "login2", " ",
                LocalDate.of(2008, 5, 13), new HashSet<>());
        Assertions.assertTrue(InMemoryUserStorage.validate(Alex));
        User Igor = new User(6, "Igor@mail.ru", "login3", null,
                LocalDate.of(2008, 5, 12), new HashSet<>());
        Assertions.assertTrue(InMemoryUserStorage.validate(Igor));
    }

    @Test
    void checkValidUserBirthday() {
        User Evgen = new User(7, "jeka@mail.ru", "login1", "Evgen",
                LocalDate.of(2008, 5, 14), new HashSet<>());
        Assertions.assertTrue(InMemoryUserStorage.validate(Evgen));
        User Alex = new User(8, "Alex@mail.ru", "login2", " ",
                LocalDate.of(2108, 5, 13), new HashSet<>());
        Assertions.assertFalse(InMemoryUserStorage.validate(Alex));

    }
}