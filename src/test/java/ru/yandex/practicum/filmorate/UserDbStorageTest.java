package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
public class UserDbStorageTest {

    @Autowired
    @Qualifier("UserDbStorage")
    UserStorage userStorage;

    @Test
    void addUserTest() throws ValidationException, UserAlreadyExistException {
        User Evgen = new User(1, "jeka@mail.ru", "login1", "Evgen",
                LocalDate.of(2008, 5, 14), new HashSet<>());
        User resultUser = userStorage.add(Evgen);
        assertEquals(1, resultUser.getId());
    }

    @Test
    void updateUserTest() throws ValidationException, UserAlreadyExistException, UserNotFoundException {
        User Evgen = new User(1, "jeka@mail.ru", "login1", "Evgen",
                LocalDate.of(2008, 5, 14), new HashSet<>());
        User Alex = new User(1, "Alex@mail.ru", "login1", "Alex",
                LocalDate.of(2008, 5, 13), new HashSet<>());
        userStorage.add(Evgen);
        userStorage.update(Alex);
        assertEquals("Alex", userStorage.getUserById(1).getName());
    }

    @Test
    void removeUserTest() throws ValidationException, UserAlreadyExistException, UserNotFoundException {
        User Evgen = new User(1, "jeka@mail.ru", "login1", "Evgen",
                LocalDate.of(2008, 5, 14), new HashSet<>());
        User Alex = new User(2, "Alex@mail.ru", "login1", "Alex",
                LocalDate.of(2008, 5, 13), new HashSet<>());
        userStorage.add(Evgen);
        userStorage.add(Alex);
        userStorage.remove(2);
        assertEquals(1, userStorage.getAll().size());
    }

    @Test
    void getAllUserTest() throws ValidationException, UserAlreadyExistException {
        User Evgen = new User(1, "jeka@mail.ru", "login1", "Evgen",
                LocalDate.of(2008, 5, 14), new HashSet<>());
        User Alex = new User(2, "Alex@mail.ru", "login1", "Alex",
                LocalDate.of(2008, 5, 13), new HashSet<>());
        userStorage.add(Evgen);
        userStorage.add(Alex);
        assertEquals(2, userStorage.getAll().size());
    }

    @Test
    void getUserByIdTest() throws UserAlreadyExistException, ValidationException, UserNotFoundException {
        User Evgen = new User(1, "jeka@mail.ru", "login1", "Evgen",
                LocalDate.of(2008, 5, 14), new HashSet<>());
        User Alex = new User(2, "Alex@mail.ru", "login1", "Alex",
                LocalDate.of(2008, 5, 13), new HashSet<>());
        userStorage.add(Evgen);
        userStorage.add(Alex);
        assertEquals(1, userStorage.getUserById(1).getId());
        assertEquals(2, userStorage.getUserById(2).getId());
    }

}