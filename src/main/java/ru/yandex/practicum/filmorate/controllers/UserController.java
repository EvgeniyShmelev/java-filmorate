package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public User add(@RequestBody @Valid User user) throws UserAlreadyExistException, ValidationException {
        return userService.add(user);
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) throws UserNotFoundException {
        return userService.update(user);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable int id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

    @DeleteMapping
    public void remove(@PathVariable("id") int id) throws UserNotFoundException {
        userService.remove(id);
    }

    @GetMapping
    public Collection<User> getAll() {
        return userService.getAll();
    }

    //Добавление в друзья
    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) throws UserNotFoundException {
        userService.addFriend(id, friendId);
    }

    //Удаление из друзей
    @DeleteMapping("/{id}/friends/{friendId}")
    public void delFriend(@PathVariable int id, @PathVariable int friendId) throws UserNotFoundException {
        userService.removeFriends(id, friendId);
    }

    //Возвращаем список пользователей, являющихся его друзьями
    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable int id) throws UserNotFoundException {
        return userService.getFriends(id);
    }

    //Возвращаем список друзей, общих с другим пользователем
    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) throws UserNotFoundException {
        return userService.getCommonFriends(id, otherId);
    }
}