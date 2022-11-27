package com.ip13.server.commands.collectionCommands;

import com.ip13.forwardedObjects.commandsEnum.COMMANDS;
import com.ip13.forwardedObjects.request.Request;
import com.ip13.forwardedObjects.response.Response;
import com.ip13.server.collection.CollectionManager;
import com.ip13.server.commands.commandsInterface.AbstractCollectionCommand;
import com.ip13.server.dataBase.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RemoveById extends AbstractCollectionCommand {
    public RemoveById(CollectionManager collectionManager, DatabaseManager databaseManager, COMMANDS commandName) {
        super(collectionManager, databaseManager, commandName);
    }

    @Override
    public Response<Boolean> execute(Request<?> request) {
        int ticket_id = (int) request.getData();

        if (!databaseManager.checkTicketOwner(ticket_id, request.getLogin())) {
            return new Response<>(commandName, "This is not your ticket, you have no rights to make operation with it", false);
        } else {
            try (PreparedStatement statement = databaseManager.getConnection().prepareStatement("DELETE FROM tickets WHERE ticket_id = ?;")) {
                statement.setInt(1, ticket_id);

                boolean result = statement.executeUpdate() > 0;

                if (result) {
                    return new Response<>(commandName, "Ticket with ID: " + ticket_id + " had been removed", true);
                } else {
                    return new Response<>(commandName, "No ticket with such ID", false);
                }
            } catch (SQLException e) {
                throw new RuntimeException("Remove by id command error", e);
            }
        }
    }
}
