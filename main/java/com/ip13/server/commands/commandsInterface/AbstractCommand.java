package com.ip13.server.commands.commandsInterface;

import com.ip13.forwardedObjects.commandsEnum.COMMANDS;
import com.ip13.forwardedObjects.request.Request;
import com.ip13.forwardedObjects.response.Response;
import com.ip13.server.collection.CollectionManager;
import com.ip13.server.dataBase.DatabaseManager;

public abstract class AbstractCommand implements Command {
    protected final CollectionManager collectionManager;
    protected final DatabaseManager databaseManager;
    protected final COMMANDS commandName;

    public AbstractCommand(CollectionManager collectionManager, DatabaseManager databaseManager, COMMANDS commandName) {
        this.collectionManager = collectionManager;
        this.databaseManager = databaseManager;
        this.commandName = commandName;
    }

    public COMMANDS getCommandName() {
        return commandName;
    }

    @Override
    public abstract Response<?> execute(Request<?> request);
}
