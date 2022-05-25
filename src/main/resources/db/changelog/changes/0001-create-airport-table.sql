--liquibase formatted sql

--changeset madars:1

CREATE TABLE airport
(
    airport VARCHAR(255) PRIMARY KEY NOT NULL UNIQUE,
    country VARCHAR(255) NOT NULL ,
    city    VARCHAR(255) NOT NULL

);