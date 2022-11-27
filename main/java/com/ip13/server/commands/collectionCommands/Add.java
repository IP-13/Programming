package com.ip13.server.commands.collectionCommands;

import com.ip13.forwardedObjects.commandsEnum.COMMANDS;
import com.ip13.forwardedObjects.request.Request;
import com.ip13.forwardedObjects.response.Response;
import com.ip13.forwardedObjects.ticket.Ticket;
import com.ip13.server.collection.CollectionManager;
import com.ip13.server.commands.commandsInterface.AbstractCollectionCommand;
import com.ip13.server.dataBase.DatabaseManager;

public class Add extends AbstractCollectionCommand {
    public Add(CollectionManager collectionManager, DatabaseManager databaseManager, COMMANDS commandName) {
        super(collectionManager, databaseManager, commandName);
    }

    @Override
    public Response<Boolean> execute(Request<?> request) {
        Ticket ticket = (Ticket) request.getData();
        String login = request.getLogin();

        if (databaseManager.addTicket(ticket, login)) {
            ticket.setId(databaseManager.getLastTicketId());
            ticket.getEvent().setId(databaseManager.getLastEventId());
        }

        boolean isAdded = collectionManager.addTicket(ticket);
        String message = isAdded ? "Your ticket was successfully added" : "Your ticket was not added";
        return new Response<>(commandName, message, isAdded);
    }

}