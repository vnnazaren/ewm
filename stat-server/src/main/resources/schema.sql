drop table if exists events cascade;
create table if not exists events
(
    id         bigint generated always as identity not null,
    app        varchar(255)                        not null,
    uri        varchar(255)                        not null,
    ip_address varchar(15)                         not null,
    event_date timestamp without time zone         not null,
    constraint pk_hits primary key (id)
);