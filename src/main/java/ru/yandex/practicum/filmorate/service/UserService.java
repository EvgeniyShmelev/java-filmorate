package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;


@Service
public class UserService {
    private UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("UserDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User add(User user) throws UserAlreadyExistException, ValidationException {
        return userStorage.add(user);
    }

    public User update(User user) throws UserNotFoundException {
        return userStorage.update(user);
    }

    public User getUserById(Integer id) throws UserNotFoundException {
        return userStorage.getUserById(id);
    }

    public void remove(Integer id) throws UserNotFoundException {
        userStorage.remove(id);
    }

    public Collection<User> getAll() {
        return userStorage.getAll();
    }


    public void addFriend(Integer id, Integer friendId) throws UserNotFoundException {
        userStorage.addFriend(id, friendId);
    }


    public void removeFriends(Integer id, Integer friendId) throws UserNotFoundException {
        userStorage.removeFriend(id, friendId);
    }

    //Возвращаем список пользователей, являющихся его друзьями
    public Collection<User> getFriends(Integer id) throws UserNotFoundException {
        return userStorage.getFriends(id);
    }

    //Получение списка общих друзей
    public Collection<User> getCommonFriends(Integer id, Integer otherId) throws UserNotFoundException {
        return userStorage.getCommonFriends(id, otherId);
    }
}