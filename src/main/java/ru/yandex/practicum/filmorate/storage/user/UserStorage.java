package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.user.User;

import java.util.Collection;

public interface UserStorage {
    User add(User user) throws UserAlreadyExistException, ValidationException;

    User update(User user) throws UserNotFoundException;

    void remove(Integer id) throws UserNotFoundException;

    User getUserById(Integer id) throws UserNotFoundException;

    Collection<User> getAll();

    //Получение друзей пользователя
    Collection<User> getFriends(Integer id) throws UserNotFoundException;

    //Добавление в друзья
    void addFriend(Integer id, Integer friendId) throws UserNotFoundException;

    //Удаление из друзей
    void removeFriend(Integer id, Integer friendId) throws UserNotFoundException;

    //Получение списка общих друзей
    Collection<User> getCommonFriends(Integer id, Integer otherId) throws UserNotFoundException;
}
