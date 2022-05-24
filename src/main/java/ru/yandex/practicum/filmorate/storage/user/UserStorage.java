package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User add(User user);

    User update(User user);

    void remove(Integer id);

    User getUserById(Integer id);

    Collection<User> getAll();

    //Получение друзей пользователя
    Collection<User> getFriends(Integer id);
}
