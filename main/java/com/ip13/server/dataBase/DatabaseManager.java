package com.ip13.server.dataBase;

import com.ip13.forwardedObjects.ticket.Event;
import com.ip13.forwardedObjects.ticket.Ticket;
import com.ip13.server.network.Client;

import java.sql.*;
import java.util.concurrent.locks.ReentrantLock;

public class DatabaseManager {
    private final Connection connection;
    private final ReentrantLock reentrantLock;

    public DatabaseManager(Connection connection, ReentrantLock reentrantLock) {
        this.connection = connection;
        this.reentrantLock = reentrantLock;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean checkTicketOwner(int ticketId, String login) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT login FROM tickets WHERE ticket_id = ?;")) {
            statement.setInt(1, ticketId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString(1).equals(login);
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Check ticket owner error", e);
        }
    }

    public boolean checkClient(Client client) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT login FROM clients WHERE login = ? AND password = ?;")
        ) {
            statement.setString(1, client.getLogin());
            statement.setString(2, client.getPassword());
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException("Check client error", e);
        }
    }

    public boolean checkLogin(String login) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT login FROM clients WHERE login = ?;")
        ) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addClient(Client client) {
        if (!checkLogin(client.getLogin())) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO clients (login, password) VALUES (?, ?);")
            ) {
                statement.setString(1, client.getLogin());
                statement.setString(2, client.getPassword());

                try {
                    reentrantLock.lock();
                    return statement.executeUpdate() > 0;
                } finally {
                    reentrantLock.unlock();
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public int getLastEventId() {
        try (PreparedStatement statement = connection.prepareStatement("SELECT CURRVAL('event_id_generator');")
        ) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Get last event ID error", e);
        }

    }

    public int getLastTicketId() {
        try (PreparedStatement statement = connection.prepareStatement("SELECT CURRVAL('ticket_id_generator');")
        ) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Get last ticket ID error", e);
        }
    }

    private boolean addEvent(Event event) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO events (event_id, name, min_age, event_type) VALUES (NEXTVAL('event_id_generator'), ?, ?, event_type(?));"
        )
        ) {
            statement.setString(1, event.getName());
            statement.setInt(2, event.getMinAge());
            statement.setString(3, event.getEventType().toString());

            try {
                reentrantLock.lock();
                return statement.executeUpdate() > 0;
            } finally {
                reentrantLock.unlock();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Add event to database error", e);
        }
    }

    public boolean addTicket(Ticket ticket, String login) {
        if (addEvent(ticket.getEvent())) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO tickets (ticket_id, coordinates, creation_date, price, discount, refundable, ticket_type, event_id, login) " +
                            "VALUES (NEXTVAL('ticket_id_generator'), (?, ?), ?, ?, ?, ?, ticket_type(?), ?, ?)"
            )
            ) {
                statement.setInt(1, ticket.getCoordinates().getX());
                statement.setDouble(2, ticket.getCoordinates().getY());
                statement.setDate(3, Date.valueOf(ticket.getCreationDate().toLocalDate()));
                statement.setDouble(4, ticket.getPrice());
                statement.setDouble(5, ticket.getDiscount());
                statement.setBoolean(6, ticket.getRefundable());
                statement.setString(7, ticket.getTicketType().toString());
                statement.setInt(8, getLastEventId());
                statement.setString(9, login);

                try {
                    reentrantLock.lock();
                    return statement.executeUpdate() > 0;
                } finally {
                    reentrantLock.unlock();
                }

            } catch (SQLException e) {
                throw new RuntimeException("Add ticket to database error", e);
            }
        }
        return false;
    }

    public void clearData() {
        try (PreparedStatement statement = connection.prepareStatement("TRUNCATE TABLE tickets, events;");
        ) {
            reentrantLock.lock();
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Clear data error", e);
        } finally {
            reentrantLock.unlock();
        }
    }

    public void init() {
        try (Statement statement = connection.createStatement()) {
            statement.execute(SQL_COMMANDS.CLEAR_DATABASE);
            statement.execute(SQL_COMMANDS.CREATE_TYPES);
            statement.execute(SQL_COMMANDS.CREATE_TABLE_EVENTS);
            statement.execute(SQL_COMMANDS.EVENT_ID_GENERATOR);
            statement.execute(SQL_COMMANDS.CREATE_TABLE_CLIENTS);
            statement.execute(SQL_COMMANDS.CREATE_TABLE_TICKETS);
            statement.execute(SQL_COMMANDS.TICKET_ID_GENERATOR);
        } catch (SQLException e) {
            throw new RuntimeException("Database initialization error", e);
        }
    }

}
