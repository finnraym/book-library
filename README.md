# Проект "Книгохранилище"

REST API по добавлению, удалению, редактированию и поиску книг, авторов и жанров.

Автор: https://github.com/finnraym

## Технологии

- Java 17
- Spring Boot 3.0.6
- Spring Data JPA, WEB, Security, Validation, AOP
- База данных PostreSQL
- Миграция данных при помощи FlyWay
- REST API - swagger openapi 3.0
- JWT
- Lombok
- Mapstruct
- JUnit 5
- Embedded database H2 (для тестирования)
- Docker

## Структура базы данных

### `Book`

| Колонка         | Тип       | Комментарий                                    |
|-----------------|-----------|------------------------------------------------|
| id              | BIGSERIAL | Уникальный идентификатор книги, первичный ключ |
| title           | Varchar   | Название книги                                 |
| year_of_issue   | Integer   | Год издание книги                              |
| number_of_pages | Integer   | Количество страниц в книге                     |

### `Author`

| Колонка       | Тип       | Комментарий                                     |
|---------------|-----------|-------------------------------------------------|
| id            | BIGSERIAL | Уникальный идентификатор автора, первичный ключ |
| first_name    | Varchar   | Имя автора                                      |
| second_name   | Varchar   | Фамилия автора                                  |
| date_of_birth | Date      | Дата рождения автора                            |

### `Genre`

| Колонка | Тип         | Комментарий                                    |
|---------|-------------|------------------------------------------------|
| id      | BIGSERIAL   | Уникальный идентификатор жанра, первичный ключ |
| name    | Varchar(60) | Название жанра                                 |

### `Users`

| Колонка  | Тип         | Комментарий                                           |
|----------|-------------|-------------------------------------------------------|
| id       | BIGSERIAL   | Уникальный идентификатор пользователя, первичный ключ |
| username | Varchar(80) | Уникальное имя пользователя                           |
| password | Varchar     | Зашифрованный пароль пользователя                     |
| role     | Varchar(50) | Роль пользователя в приложении                        |

### `Book_author`

| Колонка   | Тип    | Комментарий                                                               |
|-----------|--------|---------------------------------------------------------------------------|
| book_id   | BIGINT | Уникальный идентификатор книги, внешний ключ на поле id в таблице book    |
| author_id | BIGINT | Уникальный идентификатор автора, внешний ключ на поле id в таблице author |

### `Book_genre`

| Колонка  | Тип    | Комментарий                                                             |
|----------|--------|-------------------------------------------------------------------------|
| book_id  | BIGINT | Уникальный идентификатор книги, внешний ключ на поле id в таблице book  |
| genre_id | BIGINT | Уникальный идентификатор жанра, внешний ключ на поле id в таблице genre |


## REST API

Методы REST API реализуют [CRUD](https://en.wikipedia.org/wiki/Create,_read,_update_and_delete) интерфейс над базой данных - позволяют создавать (C - craete), читать (R - read), редактировать (U - update), удалять (D - delete).

Для просмотра полного функционала приложения: 
http://localhost:8088/swagger-ui/index.html

### Аутентификация

Аутентификация в приложении производится при помощи Jason Web Token (JWT).

#### POST `/api/v1/auth/register`

Регистрация нового пользователя без административных прав. Данные передаются в теле запроса в формате JSON:

```
{
    "username": user@mail.com",
    "password": user1234,
    "confirmedPassword": user1234
}
```

Пример ответа:
```
{
    "id": 1,
    "username": user@mail.com",
    "password": user1234
}
```
HTTP коды ответов:
- Успех - 200
- Некорректные данные в запросе (не совпали пароли) - 400
- Ошибка (например, база данных недоступна) - 500

#### POST `/api/v1/auth/login`

Вход в приложение под именем и паролем пользователя. Данные передаются в теле запроса в формате JSON:

```
{
    "username": user@mail.com",
    "password": user1234
}
```

Пример ответа:
```
{
    "id": 1,
    "username": user@mail.com",
    "accessToken": eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c,
    "refreshToken": eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c,
}
```
HTTP коды ответов:
- Успех - 200
- Некорректные данные в запросе - 400
- Ошибка авторизации - 401
- Ошибка (например, база данных недоступна) - 500

#### POST `/api/v1/auth/refresh`

Обновление access token по refresh token. Рефреш токен передается в теле запроса:

```
"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
```

Пример ответа:
```
{
    "id": 1,
    "username": "user@mail.com",
    "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
}
```
HTTP коды ответов:
- Успех - 200
- Некорректные данные в запросе - 400
- Ошибка авторизации - 401
- Ошибка (например, база данных недоступна) - 500

### Книги

#### GET `/api/v1/books`

Получение списка книг. 
В качестве параметров запроса можно передать:
- pageNo - Номер страницы (Default value: 0)
- pageSize - Количество элементов на странице (Default value: 5)
- sortBy - Название поля по которому нужно провести сортировку (Default value: id)
- sortDir - Направление сортировки (asc или desc. Default value: asc)

Пример ответа:
```
[
    {
      "pageNo": 0,
      "pageSize": 0,
      "totalElements": 0,
      "totalPages": 0,
      "last": true,
      "data": [
        {
          "id": 1,
          "title": "Effective Java",
          "yearOfIssue": 2019,
          "numberOfPages": 466,
          "authors": [
            {
              "id": 1,
              "firstName": "Joshua",
              "secondName": "Bloch",
              "dateOfBirth": "1961-08-28"
            }
          ],
          "genres": [
            {
              "id": 1,
              "name": "directory"
            }
          ]
        }
      ]
    }
]
```

HTTP коды ответов:
- Успех - 200
- Некорректные данные в запросе - 400
- Ошибка (например, база данных недоступна) - 500

#### GET `/api/v1/books/{id}`

Получение конкретной книги по id. Пример ответа:
```
{
  "id": 1,
  "title": "Effective Java",
  "yearOfIssue": 2019,
  "numberOfPages": 466,
  "authors": [
    {
      "id": 1,
      "firstName": "Joshua",
      "secondName": "Bloch",
      "dateOfBirth": "1961-08-28"
    }
  ],
  "genres": [
    {
      "id": 1,
      "name": "directory"
    }
  ]
}
```

HTTP коды ответов:
- Успех - 200
- Некорректные данные в запросе - 400
- Книга с таким id не найдена - 404
- Ошибка (например, база данных недоступна) - 500

#### POST `/api/v1/books`

Добавление новой книги в базу. Данные передаются в теле запроса в качестве JSON:


```
{
    "title": "Effective Java",
    "yearOfIssue": 2019,
    "numberOfPages": 466
}
```

Пример ответа:

```
{
    "id": 1,
    "title": "Effective Java",
    "yearOfIssue": 2019,
    "numberOfPages": 466
}
```

HTTP коды ответов:
- Успех - 200
- Некорректные данные в запросе - 400
- Ошибка (например, база данных недоступна) - 500

#### PUT `/api/v1/books`

Редактирование книги. Новые данные передаются в теле запроса в качестве JSON:

```
{
    "id": 1,
    "title": "Effective Java",
    "yearOfIssue": 2020,
    "numberOfPages": 466
}
```

HTTP коды ответов:
- Успех - 200
- Некорректные данные в запросе - 400
- Ошибка (например, база данных недоступна) - 500

#### PATCH `/api/v1/books/{bookId}/genres/{genreId}`

Добавление жанра для книги. В качестве переменных пути передаются идентификаторы книги и жанра.

HTTP коды ответов:
- Успех - 200
- Некорректные данные в запросе - 400
- Книга или жанр с такими id не найдены - 404
- Ошибка (например, база данных недоступна) - 500

#### PATCH `/api/v1/books/{bookId}/authors/{authorId}`

Добавление автора для книги. В качестве переменных пути передаются идентификаторы книги и автора.

HTTP коды ответов:
- Успех - 200
- Некорректные данные в запросе - 400
- Книга или автор с такими id не найдены - 404
- Ошибка (например, база данных недоступна) - 500

#### DELETE `/api/v1/books/{id}`

Удаление книги из базы по id.

HTTP коды ответов:
- Успех - 200
- Некорректные данные в запросе - 400
- Книга или автор с такими id не найдены - 404
- Ошибка (например, база данных недоступна) - 500

Для просмотра полного функционала приложения:
http://localhost:8088/swagger-ui/index.html