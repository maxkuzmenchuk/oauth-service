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

CREATE TABLE oauth_access_token
(
    authentication_id varchar(255) NOT NULL PRIMARY KEY,
    token_id          varchar(255) NOT NULL,
    token             bytea        NOT NULL,
    user_name         varchar(255) NOT NULL,
    client_id         varchar(255) NOT NULL,
    authentication    bytea        NOT NULL,
    refresh_token     varchar(255) NOT NULL
);

CREATE TABLE oauth_refresh_token
(
    token_id       varchar(255) NOT NULL,
    token          bytea        NOT NULL,
    authentication bytea        NOT NULL
);

