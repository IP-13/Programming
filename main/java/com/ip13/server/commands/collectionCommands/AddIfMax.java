package com.ip13.server.commands.collectionCommands;

import com.ip13.forwardedObjects.commandsEnum.COMMANDS;
import com.ip13.forwardedObjects.request.Request;
import com.ip13.forwardedObjects.response.Response;
import com.ip13.forwardedObjects.ticket.Ticket;
import com.ip13.server.collection.CollectionManager;
import com.ip13.server.commands.commandsInterface.AbstractCollectionCommand;
import com.ip13.server.dataBase.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddIfMax extends AbstractCollectionCommand {
    public AddIfMax(CollectionManager collectionManager, DatabaseManager databaseManager, COMMANDS commandName) {
        super(collectionManager, databaseManager, commandName);
    }

    @Override
    public Response<Boolean> execute(Request<?> request) {
        try (PreparedStatement statement = databaseManager.getConnection().prepareStatement("SELECT MAX(price) FROM tickets as max;")) {
            Ticket ticket = (Ticket) request.getData();
            String login = request.getLogin();
            ResultSet maxPriceResultSet = statement.executeQuery();
            double maxPrice;
            if (maxPriceResultSet.next()) {
                maxPrice = maxPriceResultSet.getDouble("max");
            } else {
                maxPrice = 0.0;
            }
            if (ticket.getPrice() > maxPrice) {
                if (databaseManager.addTicket(ticket, login)) {
                    ticket.setId(databaseManager.getLastTicketId());
                    ticket.getEvent().setId(databaseManager.getLastEventId());
                }

                boolean isAdded = collectionManager.addTicket(ticket);
                String message = isAdded ?
                        "Your ticket has the max price, and thus was successfully added" :
                        "Your ticket does not have max price, and thus was not added";
                return new Response<>(commandName, message, isAdded);
            } else {
                return new Response<>(commandName, "Your ticket does not have the max price", false);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Add if max command error", e);
        }
    }

}
