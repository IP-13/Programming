package com.ip13.server.commands.collectionCommands;

import com.ip13.forwardedObjects.commandsEnum.COMMANDS;
import com.ip13.forwardedObjects.request.Request;
import com.ip13.forwardedObjects.response.Response;
import com.ip13.forwardedObjects.ticket.*;
import com.ip13.server.collection.CollectionManager;
import com.ip13.server.commands.commandsInterface.AbstractCollectionCommand;
import com.ip13.server.dataBase.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class FilterByRefundable extends AbstractCollectionCommand {
    public FilterByRefundable(CollectionManager collectionManager, DatabaseManager databaseManager, COMMANDS commandName) {
        super(collectionManager, databaseManager, commandName);
    }

    @Override
    public Response<LinkedList<Ticket>> execute(Request<?> request) {
        Connection connection = databaseManager.getConnection();

        boolean isRefundable = (boolean) request.getData();
        String orderBy = isRefundable ? "DESC" : "ASC";

        try (PreparedStatement ticketStatement = connection.prepareStatement("SELECT * FROM tickets ORDER BY refundable " + orderBy + ";")) {
            ResultSet ticketResultSet = ticketStatement.executeQuery();

            LinkedList<Ticket> ListOfTicketsOrderedByRefundable = new LinkedList<>();

            while (ticketResultSet.next()) {
                int eventId = ticketResultSet.getInt("event_id");
                int ticketId = ticketResultSet.getInt("ticket_id");

                Ticket ticket = new Ticket(ticketId,
                        ticketResultSet.getString("name"),
                        getCoordinatesByTicketId(ticketId, connection),
                        ticketResultSet.getDate("creation_date").toLocalDate().atStartOfDay(),
                        ticketResultSet.getDouble("price"),
                        ticketResultSet.getDouble("discount"),
                        ticketResultSet.getBoolean("refundable"),
                        TicketType.valueOf(ticketResultSet.getString("ticket_type")),
                        getEventById(eventId, connection)
                );

                ListOfTicketsOrderedByRefundable.add(ticket);
            }

            return new Response<>(commandName, "List of ordered tickets", ListOfTicketsOrderedByRefundable);

        } catch (SQLException e) {
            throw new RuntimeException("Filter by refundable command error", e);
        }
    }

    private Event getEventById(int eventId, Connection connection) {
        try (PreparedStatement eventStatement = connection.prepareStatement("SELECT * FROM events WHERE event_id = ?;")) {
            eventStatement.setInt(1, eventId);
            ResultSet eventResultSet = eventStatement.executeQuery();

            eventResultSet.next();

            return new Event(eventResultSet.getInt("event_id"),
                    eventResultSet.getString("name"),
                    eventResultSet.getInt("min_age"),
                    EventType.valueOf(eventResultSet.getString("event_type"))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Coordinates getCoordinatesByTicketId(int ticketId, Connection connection) throws SQLException {
        try (
                PreparedStatement getXStatement = connection.prepareStatement("SELECT (coordinates).x from tickets where ticket_id = (?);");
                PreparedStatement getYStatement = connection.prepareStatement("SELECT (coordinates).y from tickets where ticket_id = (?);");
        ) {
            getXStatement.setInt(1, ticketId);
            ResultSet resultSetX = getXStatement.executeQuery();
            resultSetX.next();
            int x = resultSetX.getInt(1);

            getYStatement.setInt(1, ticketId);
            ResultSet resultSetY = getYStatement.executeQuery();
            resultSetY.next();
            double y = resultSetY.getDouble(1);

            return new Coordinates(x, y);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}