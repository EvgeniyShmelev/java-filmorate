package ru.yandex.practicum.filmorate.model.user;

import lombok.Data;

@Data
public class Friend {
    private int userId;
    private int friendId;
}
