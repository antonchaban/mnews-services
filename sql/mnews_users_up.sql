create table users
(
    id       bigserial
        primary key,
    password varchar(1000),
    username varchar(255)
);

alter table users
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

