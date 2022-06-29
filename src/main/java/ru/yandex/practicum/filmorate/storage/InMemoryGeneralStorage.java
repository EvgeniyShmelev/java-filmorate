package ru.yandex.practicum.filmorate.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//попытка избавиться от неверного формирования id
public abstract class InMemoryGeneralStorage<E> {
    //мапа для фильмов и юзеров
    protected Map<Integer, E> general = new ConcurrentHashMap<>();

    public Collection<E> getAll() {
        return new ArrayList<>(general.values());
    }

    //Формирование id
    public int calculateId() {
        int id = 0;
        //Поиск первого незанятого идентификатора
        for (int i = 1; i <= (general.size() + 1); i++) {
            if (!general.containsKey(i)) {
                id = i;
                break;
            }
        }
        return id;
    }
}
