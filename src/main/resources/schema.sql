--Справочная таблица жанров фильмов
CREATE TABLE IF NOT EXISTS genre (
  genre_id  int PRIMARY KEY NOT NULL,
  name      varchar NOT NULL
);

--Справочная таблица рейтингов фильмов
CREATE TABLE IF NOT EXISTS rating_mpa (
  rating_id    int PRIMARY KEY NOT NULL,
  name      varchar NOT NULL
);

--Таблица фильмов
CREATE TABLE IF NOT EXISTS film (
  film_id       int GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  name          varchar  NOT NULL,
  description   varchar,
  release_date  date  NOT NULL,
  duration      int,
  rating_id     int REFERENCES rating_mpa (rating_id) ON DELETE CASCADE,
  CONSTRAINT pk_film PRIMARY KEY (film_id)
);

--Таблица пользователей
CREATE TABLE IF NOT EXISTS "user" (
  user_id   int GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  email     varchar NOT NULL,
  login     varchar NOT NULL,
  name      varchar,
  birthday  date,
  CONSTRAINT pk_user PRIMARY KEY (user_id)
);

--Таблица лайков фильмов пользователями
CREATE TABLE IF NOT EXISTS likes (
   film_id int REFERENCES film (film_id) ON DELETE CASCADE,
   user_id int REFERENCES "user" (user_id) ON DELETE CASCADE,
   PRIMARY KEY (film_id, user_id)
);

--Таблица жанров, которым принадлежит фильм
CREATE TABLE IF NOT EXISTS film_genre (
  film_id   int  NOT NULL,
  genre_id  int  NOT NULL
);

--Таблица дружеских связей между пользователями (парная запись означает подтверждение дружбы)
CREATE TABLE IF NOT EXISTS friends (
  user_id   int REFERENCES "user" (user_id) ON DELETE CASCADE,
  friend_id int REFERENCES "user" (user_id) ON DELETE CASCADE,
  PRIMARY KEY (user_id, friend_id)
);


CREATE OR REPLACE VIEW v_film_rating_name AS
SELECT f.*, rm.name rating_name
  FROM film f, rating_mpa rm
 WHERE f.rating_id = rm.rating_id; --костыль для отображения рейтинга фильма


