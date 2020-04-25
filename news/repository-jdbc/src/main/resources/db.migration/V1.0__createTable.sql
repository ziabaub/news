create table author
(
    id      bigserial primary key not null,
    name    varchar(30)           not null,
    surname varchar(30)           not null
);
create table news
(
    id                bigserial primary key not null,
    author_id         bigint references author (id) on delete cascade,
    title             varchar(30)   not null,
    shorttext        varchar(100)  not null,
    fulltext         varchar(2000) not null,
    creationdate     varchar(50)   not null,
    modificationdate varchar(50)   not null
);
create table tag
(
    id   bigserial primary key not null,
    name varchar(30)           not null
);

create table news_tag
(
    news_id bigint references news (id) on delete set null,
    tag_id  bigint references tag (id) on delete set null,
    UNIQUE (news_id , tag_id)
);

create table roles
(
    id   bigserial primary key not null ,
    role varchar(30) not null
);

create table users
(
    roles_id  bigint primary key references roles (id) on delete cascade,
    name     varchar(20)           not null,
    surname  varchar(20)           not null,
    login    varchar(255)           not null,
    password varchar(255)           not null,
    UNIQUE (login)
);
