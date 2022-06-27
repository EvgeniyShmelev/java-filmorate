package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.friend.FriendStorage;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class FriendDbStorage implements FriendStorage {
    protected static final Logger log = LoggerFactory.getLogger(FriendDbStorage.class);

    private final JdbcTemplate jdbcTemplate;
    private final UserDbStorage userDbStorage;

    @Override
    public void add(Integer userId, Integer friendId) throws UserNotFoundException {
        User user = userDbStorage.getUserById(userId);
        User friend = userDbStorage.getUserById(friendId);

        jdbcTemplate.update("MERGE INTO friends f KEY (user_id, friend_id) VALUES (?, ?)", userId, friendId);
        log.info("Пользователь \"" + user.getName() + "\" добавил в друзья \"" + friend.getName() + "\".");
    }

    @Override
    public void remove(Integer userId, Integer friendId) throws UserNotFoundException {
        User user = userDbStorage.getUserById(userId);
        User friend = userDbStorage.getUserById(friendId);

        jdbcTemplate.update("DELETE FROM friends f WHERE friend_id = ? AND user_id = ?",
                friendId, userId);
        log.info("Пользователь: \"" + user.getName() +
                "\" удалил из друзей пользователя \"" + friend.getName() + "\".");
    }

    public Collection<User> getAllUserFriends(Integer userId) {
        Collection<User> allUserFriends = new ArrayList<>();
        SqlRowSet userRows = jdbcTemplate
                .queryForRowSet("select * " +
                        "from \"user\" " +
                        "where user_id in (select friend_id from friends where user_id = ?)", userId);

        while (userRows.next()) {
            allUserFriends.add(new User(userRows.getInt("user_id"),
                    userRows.getString("email"),
                    userRows.getString("login"),
                    userRows.getString("name"),
                    userRows.getDate("birthday").toLocalDate()));
        }
        return allUserFriends;
    }

    public Collection<User> getCommonFriends(Integer userId, Integer otherId) throws UserNotFoundException {
        User user = userDbStorage.getUserById(userId);
        User friend = userDbStorage.getUserById(otherId);
        Collection<User> ret = new ArrayList<>();
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT *" +
                " FROM \"user\" u" +
                " WHERE u.user_id in (SELECT friend_id" +
                " FROM friends f" +
                " WHERE f.user_id = ?)" +
                " AND u.user_id in (SELECT friend_id" +
                " FROM friends f" +
                " WHERE f.user_id = ?)", userId, otherId);

        while (userRows.next()) {
            ret.add(new User(userRows.getInt("user_id"),
                    userRows.getString("email"),
                    userRows.getString("login"),
                    userRows.getString("name"),
                    userRows.getDate("birthday").toLocalDate()));
        }

        return ret;
    }
}
