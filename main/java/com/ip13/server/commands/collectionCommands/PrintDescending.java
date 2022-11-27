package com.ip13.server.commands.collectionCommands;

import com.ip13.forwardedObjects.commandsEnum.COMMANDS;
import com.ip13.forwardedObjects.request.Request;
import com.ip13.forwardedObjects.response.Response;
import com.ip13.forwardedObjects.ticket.Ticket;
import com.ip13.server.collection.CollectionManager;
import com.ip13.server.commands.commandsInterface.AbstractCollectionCommand;
import com.ip13.server.dataBase.DatabaseManager;

import java.util.Comparator;
import java.util.LinkedList;

public class PrintDescending extends AbstractCollectionCommand {
    public PrintDescending(CollectionManager collectionManager, DatabaseManager databaseManager, COMMANDS commandName) {
        super(collectionManager, databaseManager, commandName);
    }

    @Override
    public Response<LinkedList<Ticket>> execute(Request<?> request) {
        LinkedList<Ticket> descendingByPrice = new LinkedList<>();
        collectionManager.getCollection().stream().sorted(Comparator.comparingDouble(Ticket::getPrice)).forEachOrdered(descendingByPrice::addFirst);
        return new Response<>(commandName, "List of sorted by price tickets", descendingByPrice);
    }

}
