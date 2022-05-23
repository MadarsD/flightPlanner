--liquibase formatted sql

--changeset madars:2

CREATE TABLE flight
(

    id int PRIMARY KEY NOT NULL UNIQUE,
    from_airport VARCHAR(255) NOT NULL,
    to_airport  VARCHAR(255) NOT NULL,
    carrier VARCHAR(255) NOT NULL,
    departure_time timestamp NOT NULL,
    arrival_time timestamp NOT NULL,
    CONSTRAINT flight_from_airport_fkey FOREIGN KEY (from_airport) REFERENCES airport (airport),
    CONSTRAINT flight_to_airport_fkey FOREIGN KEY (to_airport) REFERENCES airport (airport),
    CONSTRAINT UC_Flight UNIQUE (from_airport, to_airport, carrier, departure_time, arrival_time)

);

CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 1 INCREMENT BY 1;

