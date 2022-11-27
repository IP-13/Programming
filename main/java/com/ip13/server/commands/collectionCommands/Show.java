package com.ip13.server.commands.collectionCommands;

import com.ip13.forwardedObjects.commandsEnum.COMMANDS;
import com.ip13.forwardedObjects.request.Request;
import com.ip13.forwardedObjects.response.Response;
import com.ip13.forwardedObjects.ticket.Ticket;
import com.ip13.server.collection.CollectionManager;
import com.ip13.server.commands.commandsInterface.AbstractCollectionCommand;
import com.ip13.server.dataBase.DatabaseManager;

import java.util.LinkedList;
import java.util.List;

public class Show extends AbstractCollectionCommand {
    public Show(CollectionManager collectionManager, DatabaseManager databaseManager, COMMANDS commandName) {
        super(collectionManager, databaseManager, commandName);
    }

    @Override
    public Response<List<Ticket>> execute(Request<?> request) {
        LinkedList<Ticket> list = new LinkedList<>(collectionManager.getCollection());
        String message = list.size() > 0 ? "Success" : "No elements in collection yet";
        return new Response<>(commandName, message, list);
    }

}
