package com.ip13.server.commands.commandsInterface;

import com.ip13.forwardedObjects.commandsEnum.COMMANDS;
import com.ip13.forwardedObjects.request.Request;
import com.ip13.forwardedObjects.response.Response;
import com.ip13.server.collection.CollectionManager;
import com.ip13.server.dataBase.DatabaseManager;
import com.ip13.server.users.UsersManager;

public abstract class AbstractApplicationConnectionCommand extends AbstractCommand {
    protected final UsersManager usersManager;

    public AbstractApplicationConnectionCommand(CollectionManager collectionManager, DatabaseManager databaseManager, COMMANDS commandName, UsersManager usersManager) {
        super(collectionManager, databaseManager, commandName);
        this.usersManager = usersManager;
    }

    @Override
    public abstract Response<?> execute(Request<?> request);
}
