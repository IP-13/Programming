package com.ip13.server.commands.collectionCommands;

import com.ip13.forwardedObjects.commandsEnum.COMMANDS;
import com.ip13.forwardedObjects.request.Request;
import com.ip13.forwardedObjects.response.Response;
import com.ip13.server.collection.CollectionManager;
import com.ip13.server.commands.commandsInterface.AbstractCollectionCommand;
import com.ip13.server.dataBase.DatabaseManager;

import java.util.LinkedList;

public class History extends AbstractCollectionCommand {
    public History(CollectionManager collectionManager, DatabaseManager databaseManager, COMMANDS commandName) {
        super(collectionManager, databaseManager, commandName);
    }

    private final LinkedList<COMMANDS> commandsHistory = new LinkedList<>();

    @Override
    public Response<LinkedList<COMMANDS>> execute(Request<?> request) {
        return new Response<>(commandName, "Last 11 executed commands", commandsHistory);
    }

    public void addCommandToHistory(COMMANDS command) {
        if (commandsHistory.size() >= 11) {
            commandsHistory.removeLast();
        }
        commandsHistory.addFirst(command);
    }
}
