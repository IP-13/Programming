package com.ip13.server.dataBase;

public class SQL_COMMANDS {
    public static final String CLEAR_DATABASE = """
            DROP TABLE IF EXISTS tickets;
            DROP TABLE IF EXISTS clients;
            DROP TABLE IF EXISTS events;
            DROP SEQUENCE IF EXISTS ticket_id_generator;
            DROP SEQUENCE IF EXISTS event_id_generator;
            DROP TYPE IF EXISTS event_type;
            DROP TYPE IF EXISTS ticket_type;
            DROP TYPE IF EXISTS coordinates;
            """;

    public static final String CREATE_TYPES = """
            CREATE TYPE event_type AS ENUM ('FOOTBALL', 'OPERA', 'EXPOSITION');
            CREATE TYPE ticket_type AS ENUM ('VIP', 'USUAL', 'BUDGETARY', 'CHEAP');
            CREATE TYPE coordinates AS (x INT, y DOUBLE PRECISION);
            """;

    public static final String CREATE_TABLE_EVENTS = """
            CREATE TABLE events
            (
                event_id        INT PRIMARY KEY,
                name            VARCHAR,
                min_age         INT,
                event_type event_type
            );
            """;

    public static final String EVENT_ID_GENERATOR = """
            CREATE SEQUENCE event_id_generator AS INT START 1 INCREMENT 1 OWNED BY events.event_id;
            """;

    public static final String CREATE_TABLE_CLIENTS = """
            CREATE TABLE clients
            (
                login VARCHAR(50) PRIMARY KEY NOT NULL,
                password VARCHAR(50) NOT NULL
            );
            """;

    public static final String CREATE_TABLE_TICKETS = """
            CREATE TABLE tickets
            (
                ticket_id INT PRIMARY KEY NOT NULL,
                name VARCHAR(50),
                coordinates coordinates,
                creation_date TIMESTAMP NOT NULL,
                price DOUBLE PRECISION NOT NULL,
                discount DOUBLE PRECISION,
                refundable BOOLEAN,
                ticket_type ticket_type NOT NULL,
                event_id INT NOT NULL,
                login VARCHAR(50),
                FOREIGN KEY (event_id) REFERENCES events(event_id) ON DELETE RESTRICT,
                FOREIGN KEY (login) REFERENCES clients(login) ON DELETE RESTRICT
            );
            """;

    public static final String TICKET_ID_GENERATOR = """
            CREATE SEQUENCE ticket_id_generator AS INT START 1 INCREMENT 1 OWNED BY tickets.ticket_id;
            """;
}
