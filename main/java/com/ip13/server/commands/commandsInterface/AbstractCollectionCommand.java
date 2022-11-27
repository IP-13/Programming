package com.ip13.server.commands.commandsInterface;

import com.ip13.forwardedObjects.commandsEnum.COMMANDS;
import com.ip13.forwardedObjects.request.Request;
import com.ip13.forwardedObjects.response.Response;
import com.ip13.server.collection.CollectionManager;
import com.ip13.server.dataBase.DatabaseManager;

public abstract class AbstractCollectionCommand extends AbstractCommand {
    public AbstractCollectionCommand(CollectionManager collectionManager, DatabaseManager databaseManager, COMMANDS commandName) {
        super(collectionManager, databaseManager, commandName);
    }

    @Override
    public abstract Response<?> execute(Request<?> request);
}
