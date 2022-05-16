package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utill.ID;

import java.time.LocalDate;

@SpringBootTest
class UserControllerTest {
    
    @Test
    void checkValidUserLogin() {
        User Evgen = new User(ID.getUserId(),"jeka@mail.ru", "login1", "Evgen",
                LocalDate.of(2008, 5, 14));
        Assertions.assertTrue(UserController.validate(Evgen));
        User Alex = new User(ID.getUserId(),"Alex@mail.ru", null, "Alex",
                LocalDate.of(2008, 5, 13));
        Assertions.assertFalse(UserController.validate(Alex));
        User Igor = new User(ID.getUserId(),"Igor@mail.ru", " ", "Igor",
                LocalDate.of(2008, 5, 12));
        Assertions.assertFalse(UserController.validate(Igor));
    }

    @Test
    void checkValidUserEmail() {
        User Evgen = new User(ID.getUserId(),"jeka@mail.ru", "login1", "Evgen",
                LocalDate.of(2008, 5, 14));
        Assertions.assertTrue(UserController.validate(Evgen));
        User Alex = new User(ID.getUserId(),"Alexmail.ru", "login2", "Alex",
                LocalDate.of(2008, 5, 13));
        Assertions.assertFalse(UserController.validate(Alex));
    }

    @Test
    void checkValidUserName() {
        User Evgen = new User(ID.getUserId(),"jeka@mail.ru", "login1", "Evgen",
                LocalDate.of(2008, 5, 14));
        Assertions.assertTrue(UserController.validate(Evgen));
        User Alex = new User(ID.getUserId(),"Alex@mail.ru", "login2", " ",
                LocalDate.of(2008, 5, 13));
        Assertions.assertTrue(UserController.validate(Alex));
        User Igor = new User(ID.getUserId(),"Igor@mail.ru", "login3", null,
                LocalDate.of(2008, 5, 12));
        Assertions.assertTrue(UserController.validate(Igor));
    }

    @Test
    void checkValidUserBirthday() {
        User Evgen = new User(ID.getUserId(),"jeka@mail.ru", "login1", "Evgen",
                LocalDate.of(2008, 5, 14));
        Assertions.assertTrue(UserController.validate(Evgen));
        User Alex = new User(ID.getUserId(),"Alex@mail.ru", "login2", " ",
                LocalDate.of(2108, 5, 13));
        Assertions.assertFalse(UserController.validate(Alex));

    }
}