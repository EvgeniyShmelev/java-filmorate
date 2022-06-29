MERGE INTO genre KEY (genre_id) VALUES (1, 'Комедия');
MERGE INTO genre KEY (genre_id) VALUES (2, 'Драма');
MERGE INTO genre KEY (genre_id) VALUES (3, 'Мультфильм');
MERGE INTO genre KEY (genre_id) VALUES (4, 'Триллер');
MERGE INTO genre KEY (genre_id) VALUES (5, 'Документальный');
MERGE INTO genre KEY (genre_id) VALUES (6, 'Боевик');

MERGE INTO rating_mpa KEY (rating_id) VALUES (1, 'G');
MERGE INTO rating_mpa KEY (rating_id) VALUES (2, 'PG');
MERGE INTO rating_mpa KEY (rating_id) VALUES (3, 'PG-13');
MERGE INTO rating_mpa KEY (rating_id) VALUES (4, 'R');
MERGE INTO rating_mpa KEY (rating_id) VALUES (5, 'NC-17');


--я например хочу чтоб у меня таблицы с жанрами и мпа заполнялись из data.sql.
--написал там инсертов, все хорошо, но  как сделать чтоб если они там уже есть,
--то больше не инсертить. то есть если на пустой базе запускается прилка,
--то инсертить, если не на пустой - то нет?