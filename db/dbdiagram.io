Table users {
  id bigint [pk, increment]
  name varchar(250) [not null, note: 'Имя']
  email varchar(254) [unique, not null, note: 'Почтовый адрес']

  Note {
    'Пользователи'
    }
}

Table categories {
  id bigint [pk, increment]
  name varchar(50) [unique, not null, note: 'Название категории']

  Note {
    'Категории событий'
    }
}

Table compilations {
  id bigint [pk, increment]
  pinned boolean [not null, default: false, note: 'Закреплена ли подборка на главной странице сайта']
  title varchar(50) [not null, note: 'Заголовок подборки']

  Note {
    'Подборки событий'
    }
}

Table locations {
  id bigint [pk, increment]
  lat float [not null, note: 'Широта']
  lon float [not null, note: 'Долгота']

  Note {
    'Координаты мест событий'
    }
}

Table events {
  id bigint [pk, increment]
  annotation varchar(2000) [not null, note: 'Краткое описание']
  category_id bigint [not null, ref: > categories.id, note: 'Id категории к которой относится событие']
  confirmed_requests integer [note: 'Количество одобренных заявок на участие в данном событии']
  created_on timestamp [not null, note: 'Дата и время создания события']
  description varchar(7000) [not null, note: 'Полное описание события']
  event_date timestamp [not null, note: 'Дата и время на которые намечено событие']
  initiator_id bigint [not null, ref: > users.id, note: 'Id инициатора']
  location_id bigint [not null, ref: > locations.id, note: 'Id места проведения события']
  paid boolean [not null, note: 'Нужно ли оплачивать участие']
  participant_limit integer [default: 0, note: 'Ограничение на количество участников. Значение 0 - означает отсутствие ограничения']
  published_on timestamp [note: 'Дата и время публикации события']
  request_moderation boolean [default: true, note: 'Нужна ли пре-модерация заявок на участие']
  state varchar(50) [not null, note: 'Список состояний жизненного цикла события']
  title varchar(120) [not null, note: 'Заголовок']
  views integer [default: 0, note: 'Количество просмотров события']

  Note {
    'События'
    }
}

Table requests {
  id bigint [pk, increment]
  created timestamp [not null, note: 'Дата и время создания заявки']
  event_id bigint [not null, ref: > events.id, note: 'Идентификатор события']
  requester_id bigint [not null, ref: > users.id, note: 'Идентификатор пользователя, отправившего заявку']
  status varchar(10) [not null, note: 'Статус заявки']

  Note {
    'Запросы на участие в событиях'
    }
}

Table compilations_events {
  event_id bigint [pk, ref: > events.id, note: 'Идентификатор события']
  compilation_id bigint [pk, ref: > compilations.id, note: 'Идентификатор подборки событий']

  Note {
    'Связь событий и подборок событий'
    }
}