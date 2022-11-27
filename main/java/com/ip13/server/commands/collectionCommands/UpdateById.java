package com.ip13.server.commands.collectionCommands;

import com.ip13.forwardedObjects.commandsEnum.COMMANDS;
import com.ip13.forwardedObjects.request.Request;
import com.ip13.forwardedObjects.response.Response;
import com.ip13.forwardedObjects.ticket.Event;
import com.ip13.forwardedObjects.ticket.Ticket;
import com.ip13.server.collection.CollectionManager;
import com.ip13.server.commands.commandsInterface.AbstractCollectionCommand;
import com.ip13.server.dataBase.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateById extends AbstractCollectionCommand {
    public UpdateById(CollectionManager collectionManager, DatabaseManager databaseManager, COMMANDS commandName) {
        super(collectionManager, databaseManager, commandName);
    }

    @Override
    public Response<Integer> execute(Request<?> request) {
        Ticket ticket = (Ticket) request.getData();

        if (!databaseManager.checkTicketOwner(ticket.getId(), request.getLogin())) {
            return new Response<>(commandName, "This is not your ticket, you have no rights to make operation with it", 0);
        } else {
            try (PreparedStatement updateTicket = databaseManager.getConnection().prepareStatement(
                    "UPDATE tickets SET name = (?), coordinates = ((?), (?)), price = (?), discount = (?), refundable = (?), ticket_type = ticket_type(?) WHERE ticket_id = (?);");

                 PreparedStatement updateEvent = databaseManager.getConnection().prepareStatement(
                         "UPDATE events SET name = (?), min_age = (?), event_type = event_type(?) WHERE event_id = (?);")
            ) {
                updateTicket.setString(1, ticket.getName());
                updateTicket.setInt(2, ticket.getCoordinates().getX());
                updateTicket.setDouble(3, ticket.getCoordinates().getY());
                updateTicket.setDouble(4, ticket.getPrice());
                updateTicket.setDouble(5, ticket.getDiscount());
                updateTicket.setBoolean(6, ticket.getRefundable());
                updateTicket.setString(7, ticket.getTicketType().toString());
                updateTicket.setInt(8, ticket.getId());

                Event event = ticket.getEvent();

                updateEvent.setString(1, event.getName());
                updateEvent.setInt(2, event.getMinAge());
                updateEvent.setString(3, event.getEventType().toString());
                updateEvent.setInt(4, event.getId());

                int updates = updateTicket.executeUpdate() + updateEvent.executeUpdate();

                if (updates > 0) {
                    return new Response<>(commandName, "Ticket with ID: " + ticket.getId() + " had been updates", updates);
                } else {
                    return new Response<>(commandName, "No ticket with such ID", updates);
                }
            } catch (SQLException e) {
                throw new RuntimeException("Update by id command error", e);
            }
        }
    }
}
