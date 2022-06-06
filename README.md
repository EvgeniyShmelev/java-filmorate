# java-filmorate
Template repository for Filmorate project.
# Filmorate

### Проект - аналог кинопоиска

Схема базы данных:

![BD](/src/main/resources/BD.png?raw=true "BD")

Примеры обращения к данным:
### Получение списка пользователей

```sql
    SELECT *
    FROM users;
```
### Получение списка друзей пользователя

````
SELECT *
FROM users 
WHERE users.user_id in (SELECT friend_id
FROM friends 
WHERE friends.user_id = friends.friend_id
AND friends.is_friends = true);
````
