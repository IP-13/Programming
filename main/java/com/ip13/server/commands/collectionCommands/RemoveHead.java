package com.ip13.server.commands.collectionCommands;

import com.ip13.forwardedObjects.commandsEnum.COMMANDS;
import com.ip13.forwardedObjects.request.Request;
import com.ip13.forwardedObjects.response.Response;
import com.ip13.server.collection.CollectionManager;
import com.ip13.server.commands.commandsInterface.AbstractCollectionCommand;
import com.ip13.server.dataBase.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RemoveHead extends AbstractCollectionCommand {
    public RemoveHead(CollectionManager collectionManager, DatabaseManager databaseManager, COMMANDS commandName) {
        super(collectionManager, databaseManager, commandName);
    }

    @Override
    public Response<Integer> execute(Request<?> request) {
        try (
                PreparedStatement statement = databaseManager.getConnection().prepareStatement
                        ("DELETE FROM tickets where price = (SELECT MAX(price) from tickets);")
        ) {
            int removedTickets = statement.executeUpdate();
            if (removedTickets > 0) {
                for (int i = 0; i < removedTickets; i++) {
                    collectionManager.getCollection().poll();
                }

                return new Response<>(commandName, removedTickets + " tickets had been removed", removedTickets);
            }
            return new Response<>(commandName, "No items in collection yet", removedTickets);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}