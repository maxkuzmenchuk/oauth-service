-- auto-generated definition
create table app_user
(
    app_user_id bigserial
        primary key,
    is_active   boolean,
    password    varchar(255),
    username    varchar(255)
);

alter table app_user
    owner to postgres;

-- auto-generated definition
create table app_user_roles
(
    user_id bigint not null
        constraint fk3lwiahkol5aetc57pto5olacf
            references app_user,
    roles   varchar(255)
);

alter table app_user_roles
    owner to postgres;

