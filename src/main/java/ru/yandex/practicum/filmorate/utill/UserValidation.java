package ru.yandex.practicum.filmorate.utill;

import org.springframework.util.StringUtils;
import ru.yandex.practicum.filmorate.model.user.User;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class UserValidation {
    public static boolean validate(User user) {
        return StringUtils.hasText(user.getEmail())
                && StringUtils.hasText(user.getLogin())
                && Pattern.compile("(.+@.+\\..+)").matcher(user.getEmail()).matches()
                && (user.getBirthday().isBefore(LocalDate.now()));
    }
}
