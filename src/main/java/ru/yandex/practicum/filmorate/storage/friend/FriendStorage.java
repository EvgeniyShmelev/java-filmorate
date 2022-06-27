package ru.yandex.practicum.filmorate.storage.friend;

import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;

import java.util.Collection;

public interface FriendStorage {
    void add(Integer id, Integer friendId) throws UserNotFoundException;

    void remove(Integer id, Integer friendId) throws UserNotFoundException;

    Collection<User> getAllUserFriends(Integer id);

    Collection<User> getCommonFriends(Integer id, Integer friendId) throws UserNotFoundException;
}