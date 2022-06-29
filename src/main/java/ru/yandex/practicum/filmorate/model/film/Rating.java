package ru.yandex.practicum.filmorate.model.film;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    private int id;
    private String name;
    public Rating(int id){
        this.id = id;
    }
    public Rating(String name){
        this.name = name;
    }
}
