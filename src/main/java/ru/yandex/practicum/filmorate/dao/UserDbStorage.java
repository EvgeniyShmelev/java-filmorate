package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.utill.NumberGenerator;
import ru.yandex.practicum.filmorate.utill.UserValidation;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Collection;

@Component("UserDbStorage")
@Repository
@Slf4j
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User add(User user) {
        if (!UserValidation.validate(user)) {
            throw new ValidationException("ошибка");
        }
        user.setId(NumberGenerator.getUserId());
        jdbcTemplate.update("insert into \"user\" (user_id, email, login, name, birthday) values (?,?,?,?,?)",
                user.getId(),
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday());

        log.info("Создан пользователь: {}", user);
        return user;
    }

    @Override
    public User update(User user) throws UserNotFoundException {
        int id = user.getId();
        if (user.getId() < 1) {
            log.error("Пользователь {} не найден!", id);
            throw new UserNotFoundException("user с id " + id + " не найден!");
        }
        jdbcTemplate.update("update \"user\" set email = ?," +
                        " login = ?," +
                        " name = ?," +
                        " birthday = ?",
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday());

        log.info("Обновлен пользователь: {}", user);
        return user;
    }

    @Override
    public void remove(Integer id) {
        jdbcTemplate.update("delete from \"user\" where user_id = ?", id);
        log.info("Удален пользователь: {}", id);
    }

    @Override
    public User getUserById(Integer id) throws UserNotFoundException {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from \"user\" where user_id = ?", id);
        User user = null;
        if (userRows.next()) {
            user = new User(userRows.getInt("user_id"),
                    userRows.getString("email"),
                    userRows.getString("login"),
                    userRows.getString("name"),
                    userRows.getDate("birthday").toLocalDate());

            return user;
        } else {
            log.error("Пользователь {} не найден!", id);
            throw new UserNotFoundException("Пользователь #" + id + " не найден!");
        }
    }

    @Override
    public Collection<User> getAll() {
        Collection<User> users = new ArrayList<>();
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from \"user\"");
        while (userRows.next()) {
            users.add(new User(
                    userRows.getInt("user_id"),
                    userRows.getString("email"),
                    userRows.getString("login"),
                    userRows.getString("name"),
                    userRows.getDate("birthday").toLocalDate()));
        }
        return users;
    }

    @Override
    public Collection<User> getFriends(Integer id) {
        Collection<User> ret = new ArrayList<>();
        SqlRowSet userRows = jdbcTemplate
                .queryForRowSet("select * " +
                        "from \"user\" " +
                        "where user_id in (select friend_id from friends where user_id = ?)", id);

        while (userRows.next()) {
            ret.add(new User(userRows.getInt("user_id"),
                    userRows.getString("email"),
                    userRows.getString("login"),
                    userRows.getString("name"),
                    userRows.getDate("birthday").toLocalDate()));
        }

        return ret;
    }

    @Override
    public void addFriend(Integer id, Integer friendId) throws UserNotFoundException {
        User user = getUserById(id);
        User friend = getUserById(friendId);

        jdbcTemplate.update("MERGE INTO friends f KEY (user_id, friend_id) VALUES (?, ?)", id, friendId);
        log.info("Пользователь {}  добавил в друзья пользователя {}",user.getName(), friend.getName());
    }

    @Override
    public void removeFriend(Integer id, Integer friendId) throws UserNotFoundException {
        User user = getUserById(id);
        User friend = getUserById(friendId);

        jdbcTemplate.update("DELETE FROM friends f WHERE friend_id = ? AND user_id = ?", friendId, id);
        log.info("Пользователь {}  удалил из друзей пользователя {}",user.getName(), friend.getName());
    }

    //Получение списка общих друзей
    @Override
    public Collection<User> getCommonFriends(Integer id, Integer otherId) throws UserNotFoundException {
        User user = getUserById(id);
        User friend = getUserById(otherId);
        Collection<User> commonFriends = new ArrayList<>();
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT *" +
                " FROM \"user\" u" +
                " WHERE u.user_id in (SELECT friend_id" +
                " FROM friends f" +
                " WHERE f.user_id = ?)" +
                " AND u.user_id in (SELECT friend_id" +
                " FROM friends f" +
                " WHERE f.user_id = ?)", id, otherId);

        while (userRows.next()) {
            commonFriends.add(new User(userRows.getInt("user_id"),
                    userRows.getString("email"),
                    userRows.getString("login"),
                    userRows.getString("name"),
                    userRows.getDate("birthday").toLocalDate()));
        }

        return commonFriends;
    }

}
