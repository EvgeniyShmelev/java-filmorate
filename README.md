# Filmorate
___

## Проект - аналог кинопоиска
___

Схема базы данных:

![BD](/src/main/resources/BD.png?raw=true "BD")

Примеры обращения к данным:
### Получение списка пользователей

````sql
    SELECT *
    FROM users;
````

### Получение пользователя по id

```sql
SELECT *
FROM users 
WHERE user_id = {user_id}; --значение id должно поступить от пользователя
```

### Получение списка друзей пользователя

```sql
SELECT *
FROM users 
WHERE users.user_id in (
SELECT friend_id
FROM friends 
WHERE user_id = {user_id}
AND is_friends = 1);
```

### Получение списка общих друзей пользователей c user_id 1 и 2

```sql
SELECT *
FROM users 
WHERE users.user_id in (
SELECT friend_id
FROM friends 
WHERE friends.user_id = 1
AND friends.is_friends = 1)
AND users.user_id in (
SELECT friends.friend_id
FROM friends 
WHERE friends.user_id = 2
AND friends.is_friends = 1);
```