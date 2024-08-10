--liquibase formatted sql

--changeset yumeinaruu:1
--comment table product created
create table product
(
    id          bigserial not null
        constraint product_pk
            primary key,
    name        varchar   not null,
    description varchar,
    price       double precision
);