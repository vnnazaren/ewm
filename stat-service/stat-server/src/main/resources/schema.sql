drop table if exists hits cascade;
create table if not exists hits
(
    id         bigint generated always as identity not null,
    app        varchar(255)                        not null,
    uri        varchar(255)                        not null,
    ip_address varchar(15)                         not null,
    hit_date   timestamp without time zone         not null,
    constraint pk_hits primary key (id)
);