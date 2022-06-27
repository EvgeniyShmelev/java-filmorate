package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.InMemoryGeneralStorage;

import java.util.*;

import static ru.yandex.practicum.filmorate.utill.UserValidation.validate;


@Component
@Slf4j
public class InMemoryUserStorage extends InMemoryGeneralStorage<User> implements UserStorage {

    @Override
    public User add(User user) throws UserAlreadyExistException, ValidationException {
        if (validate(user)) {
            if (general.containsKey(user.getId())) {
                log.error("User с ID #" + user.getId() + " уже существует!");
                throw new UserAlreadyExistException("Такой user уже существует.");
            }
            if (user.getId() <= 0) {
                user.setId(calculateId());
            }
            general.put(user.getId(), user);
            log.info("Добавлен user: " + user);
        } else {
            throw new ValidationException("Данные не верны");
        }
        return user;
    }

    @Override
    public User update(User user) throws UserNotFoundException {
        if (general.containsKey(user.getId())) {
            //для проверки на отрицательный id
            general.replace(user.getId(), user);
            log.info("Обновлен user: " + user);
            return user;
        } else {
            log.error("user с id " + user.getId() + " не найден!");
            throw new UserNotFoundException("user с id " + user.getId() + " не найден!");
        }
    }

    @Override
    public void remove(Integer id) throws UserNotFoundException {
        if (general.containsKey(id)) {
            general.remove(id);
            log.info("Удален " + general.get(id));
        } else {
            throw new UserNotFoundException("user с id " + id + " не найден!");
        }
    }

    @Override
    public User getUserById(Integer id) throws UserNotFoundException {
        if (general.containsKey(id)) {
            return general.get(id);
        } else {
            throw new UserNotFoundException("user с id " + id + " не найден!");
        }
    }

    //Получение друзей пользователя
    public List<User> getFriends(Integer id) throws UserNotFoundException {
        List<User> userFriends = new ArrayList<>();
        if (getUserById(id).getFriends() != null) {
            for (Integer userFriend : getUserById(id).getFriends())
                userFriends.add(getUserById(userFriend));
        }
        return userFriends;
    }

    public void addFriend(Integer id, Integer friendId) throws UserNotFoundException {
        User user = getUserById(id);
        user.getFriends().add(friendId);
    }

    public void removeFriend(Integer id, Integer friendId) throws UserNotFoundException {
        User user = getUserById(id);
        user.getFriends().remove(friendId);
    }

    public List<User> getCommonFriends(Integer id, Integer otherId) throws UserNotFoundException {
        User user = getUserById(id);
        User other = getUserById(otherId);

        List<User> commonFriends = new ArrayList<>();
        if (user.getFriends() != null && other.getFriends() != null) {
            for (Integer i : user.getFriends()) {
                if (other.getFriends().contains(i))
                    commonFriends.add(getUserById(i));
            }
        }
        return commonFriends;
    }
}
