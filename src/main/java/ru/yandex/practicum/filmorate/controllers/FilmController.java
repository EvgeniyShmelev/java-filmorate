package ru.yandex.practicum.filmorate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/films")
public class FilmController {
    private FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film add(@RequestBody @Valid Film film) throws ValidationException, FilmAlreadyExistException {
        return filmService.add(film);
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) throws FilmNotFoundException {
        return filmService.update(film);
    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable("id") Integer id) throws FilmNotFoundException {
        filmService.remove(id);
    }

    @GetMapping("/{id}")
    public Film findFilm(@PathVariable int id) throws FilmNotFoundException, EntityNotFoundException {
        return filmService.get(id);
    }

    @GetMapping
    public Collection<Film> getAll() {
        return filmService.getAll();
    }

    //пользователь ставит лайк фильму
    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) throws FilmNotFoundException, UserNotFoundException, EntityNotFoundException {
        filmService.addLike(id, userId);
    }

    //пользователь удаляет лайк
    @DeleteMapping("{id}/like/{userId}")
    public void removeLike(@PathVariable int id, @PathVariable int userId) throws FilmNotFoundException, UserNotFoundException, EntityNotFoundException {
        filmService.removeLike(id, userId);
    }

    //Возвращает список из первых count фильмов по количеству лайков.
    //Если значение параметра count не задано - первые 10
    @GetMapping("/popular")
    public Collection<Film> getPopular(@RequestParam(defaultValue = "10", required = false) int count) {
        return filmService.getMostPopular(count);
    }
}