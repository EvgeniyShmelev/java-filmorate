package ru.yandex.practicum.filmorate.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yandex.practicum.filmorate.exceptions.FilmAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.TreeSet;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FilmControllerTestMockMvc {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void add() throws Exception {
        Film film1 = new Film(1, "Jack Sparrow 1", "pirate 1",
                LocalDate.of(2003, 1, 1), 120, new TreeSet<>());
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/films")
                                .content(objectMapper.writeValueAsString(film1))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void nullName() throws Exception {
        Film film2 = new Film(2, null, "pirate 2",
                LocalDate.of(2004, 1, 1), 120, new TreeSet<>());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/films")
                                .content(objectMapper.writeValueAsString(film2))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(ValidationException.class));
    }

    @Test
    public void emptyName() throws Exception {
        Film film3 = new Film(3, " ", "pirate 3",
                LocalDate.of(2005, 1, 1), 120, new TreeSet<>());
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/films")
                                .content(objectMapper.writeValueAsString(film3))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(ValidationException.class));
    }

    @Test
    public void emptyDescription() throws Exception {
        Film film4 = new Film(4, "Jack Sparrow 3 ", "",
                LocalDate.of(1900, 3, 25), 200, new TreeSet<>());
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/films")
                                .content(objectMapper.writeValueAsString(film4))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(ValidationException.class));
    }

    @Test
    public void descriptionMoreThan200() throws Exception {
        String testDescription = "1".repeat(201);
        Film film5 = new Film(5, "Jack Sparrow 2", testDescription,
                LocalDate.of(2004, 1, 1), 120, new TreeSet<>());
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/films")
                                .content(objectMapper.writeValueAsString(film5))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(ValidationException.class));
    }

    @Test
    public void negativeDuration() throws Exception {
        Film film6 = new Film(6, "Jack Sparrow 2", "pirate 2",
                LocalDate.of(2004, 1, 1), -120, new TreeSet<>());
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/films")
                                .content(objectMapper.writeValueAsString(film6))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(ValidationException.class));
    }

    @Test
    public void oldReleaseDate() throws Exception {
        Film film7 = new Film(7, "Jack Sparrow 2", "pirate 2",
                LocalDate.of(1895, 12, 27), 120, new TreeSet<>());
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/films")
                                .content(objectMapper.writeValueAsString(film7))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(ValidationException.class));
    }

    @Test
    public void updateNotExist() throws Exception {
        Film film1 = new Film(1, "Jack Sparrow 1", "pirate 1",
                LocalDate.of(2003, 1, 1), 120, new TreeSet<>());
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/films")
                                .content(objectMapper.writeValueAsString(film1))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(FilmNotFoundException.class));
    }

    @Test
    public void filmAddAlready() throws Exception {
        Film film1 = new Film(1, "Jack Sparrow 1", "pirate 1",
                LocalDate.of(2003, 1, 1), 120, new TreeSet<>());
        Film film2 = new Film(1, "Jack Sparrow 1", "pirate 1",
                LocalDate.of(2003, 1, 1), 120, new TreeSet<>());
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/films")
                                .content(objectMapper.writeValueAsString(film1))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/films")
                                .content(objectMapper.writeValueAsString(film2))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(mvcResult -> mvcResult.getResolvedException().getClass().equals(FilmAlreadyExistException.class));
    }
}