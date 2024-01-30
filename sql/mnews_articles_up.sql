create table users
(
    id       bigserial
        primary key,
    password varchar(1000),
    username varchar(255)
);

alter table users
    owner to postgres;

create table articles
(
    id             bigserial
        primary key,
    article_date   timestamp(6),
    description_en varchar(2048),
    description_ua varchar(2048),
    link           varchar(255),
    source         varchar(255),
    title_en       varchar(255),
    title_ua       varchar(255),
    user_id        bigint
        constraint fklc3sm3utetrj1sx4v9ahwopnr
            references users
);

alter table articles
    owner to postgres;

create table category
(
    id         bigint not null
        constraint fk4335om405la1e2i4u62c0oiok
            references articles,
    categories varchar(255)
        constraint category_categories_check
            check ((categories)::text = ANY
                   ((ARRAY ['CATEGORY_WAR'::character varying, 'CATEGORY_SPORT'::character varying, 'CATEGORY_ECONOMY'::character varying, 'CATEGORY_ENTERTAINMENT'::character varying, 'CATEGORY_SCIENCE'::character varying, 'CATEGORY_OTHER'::character varying])::text[]))
);

alter table category
    owner to postgres;

create table role
(
    id    bigint not null
        constraint fkmkvuwweyisdgw5vulplv5aven
            references users,
    roles varchar(255)
        constraint role_roles_check
            check ((roles)::text = ANY
                   ((ARRAY ['ROLE_EDITOR'::character varying, 'ROLE_ADMIN'::character varying])::text[]))
);

alter table role
    owner to postgres;

