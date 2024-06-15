
Table "USERS" {
  "ID" BIGINT [pk, not null, increment]
  "NAME" VARCHAR(250) [not null, note: 'Имя']
  "EMAIL" VARCHAR(254) [unique, not null, note: 'Почтовый адрес']
  Note: 'Пользователи'
}

Table "CATEGORIES" {
  "ID" BIGINT [pk, not null, increment]
  "NAME" VARCHAR(50) [unique, not null, note: 'Название категории']
  Note: 'Категории событий'
}

Table "COMPILATIONS" {
  "ID" BIGINT [pk, not null, increment]
  "PINNED" BOOLEAN [not null, default: FALSE, note: 'Закреплена ли подборка на главной странице сайта']
  "TITLE" VARCHAR(50) [not null, note: 'Заголовок подборки']
  Note: 'Подборки событий'
}

Table "LOCATIONS" {
  "ID" BIGINT [pk, not null, increment]
  "LAT" FLOAT [not null, note: 'Широта']
  "LON" FLOAT [not null, note: 'Долгота']
  Note: 'Координаты мест событий'
}

Table "EVENTS" {
  "ID" BIGINT [pk, not null, increment]
  "ANNOTATION" VARCHAR(2000) [not null, note: 'Краткое описание']
  "CATEGORY_ID" BIGINT [not null, note: 'Id категории к которой относится событие']
  "CONFIRMED_REQUESTS" INTEGER [note: 'Количество одобренных заявок на участие в данном событии']
  "CREATED_ON" TIMESTAMP [not null, note: 'Дата и время создания события']
  "DESCRIPTION" VARCHAR(7000) [not null, note: 'Полное описание события']
  "EVENT_DATE" TIMESTAMP [not null, note: 'Дата и время на которые намечено событие']
  "INITIATOR_ID" BIGINT [not null, note: 'Id инициатора']
  "LOCATION_ID" BIGINT [not null, note: 'Id места проведения события']
  "PAID" BOOLEAN [not null, note: 'Нужно ли оплачивать участие']
  "PARTICIPANT_LIMIT" INTEGER [default: 0, note: 'Ограничение на количество участников. Значение 0 - означает отсутствие ограничения']
  "PUBLISHED_ON" TIMESTAMP [note: 'Дата и время публикации события']
  "REQUEST_MODERATION" BOOLEAN [default: TRUE, note: 'Нужна ли пре модерация заявок на участие']
  "STATE" VARCHAR(50) [not null, note: 'Список состояний жизненного цикла события']
  "TITLE" VARCHAR(120) [not null, note: 'Заголовок']
  "VIEWS" INTEGER [default: 0, note: 'Количество просмотров события']
  Note: 'События'
}

Table "REQUESTS" {
  "ID" BIGINT [pk, not null, increment]
  "CREATED" TIMESTAMP [not null, note: 'Дата и время создания заявки']
  "EVENT_ID" BIGINT [not null, note: 'Идентификатор события']
  "REQUESTER_ID" BIGINT [not null, note: 'Идентификатор пользователя, отправившего заявку']
  "STATUS" VARCHAR(10) [not null, note: 'Статус заявки']
  Note: 'Запросы на участие в событиях'
}

Table "COMPILATIONS_EVENTS" {
  "COMPILATION_ID" BIGINT [note: 'Идентификатор подборки событий']
  "EVENT_ID" BIGINT [note: 'Идентификатор события']

  Indexes {
    (COMPILATION_ID, EVENT_ID) [pk]
  }
  Note: 'Связь событий и подборок событий'
}

Table "COMMENTS" {
  "ID" BIGINT [pk, not null, increment]
  "CREATED" TIMESTAMP
  "EVENT_ID" BIGINT
  "AUTHOR_ID" BIGINT
  "TEXT" VARCHAR(2000) [not null]
}

Ref "COMMENTS":"EVENTS"."ID" < "COMMENTS"."EVENT_ID" [delete: cascade]

Ref "COMMENTS":"USERS"."ID" < "COMMENTS"."AUTHOR_ID" [delete: cascade]

Ref:"CATEGORIES"."ID" < "EVENTS"."CATEGORY_ID"

Ref:"USERS"."ID" < "EVENTS"."INITIATOR_ID"

Ref:"LOCATIONS"."ID" < "EVENTS"."LOCATION_ID"

Ref:"EVENTS"."ID" < "REQUESTS"."EVENT_ID"

Ref:"USERS"."ID" < "REQUESTS"."REQUESTER_ID"

Ref:"COMPILATIONS"."ID" < "COMPILATIONS_EVENTS"."COMPILATION_ID"

Ref:"EVENTS"."ID" < "COMPILATIONS_EVENTS"."EVENT_ID"
