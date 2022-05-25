package ru.yandex.practicum.filmorate.storage.film.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {
    private UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
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

    //Добавление в друзья
    public void addFriend(Integer id, Integer friendId) throws UserNotFoundException {
        //То есть если Лена стала другом Саши, то это значит, что Саша теперь друг Лены.
        User user = userStorage.getUserById(id);
        User friend = userStorage.getUserById(friendId);

        if (user.getFriends() == null) user.setFriends(new HashSet<>());
        if (friend.getFriends() == null) friend.setFriends(new HashSet<>());

        if (!user.getFriends().contains(friend.getId())) {
            user.getFriends().add(friend.getId());

            if (!friend.getFriends().contains(user.getId()))
                friend.getFriends().add(user.getId());
        }
    }

    //Удаление из друзей
    public void removeFriends(Integer id, Integer friendId) throws UserNotFoundException {
        User user = userStorage.getUserById(id);
        User friend = userStorage.getUserById(friendId);

        if (!user.getFriends().contains(friend.getId())) {
            user.getFriends().remove(friend.getId());

            if (!friend.getFriends().contains(user.getId()))
                friend.getFriends().remove(user.getId());
        }
    }

    //Возвращаем список пользователей, являющихся его друзьями
    public Collection<User> getFriends(Integer id) throws UserNotFoundException {
        return userStorage.getFriends(id);
    }

    //Получение списка общих друзей
    public Collection<User> getMutualFriends(Integer id, Integer otherId) throws UserNotFoundException {
        User user = userStorage.getUserById(id);
        User other = userStorage.getUserById(otherId);

        List<User> mutualFriends = new ArrayList<>();
        if (user.getFriends() != null && other.getFriends() != null) {
            for (Integer i : user.getFriends()) {
                if (other.getFriends().contains(i))
                    mutualFriends.add(userStorage.getUserById(i));
            }
        }
        return mutualFriends;
    }
}