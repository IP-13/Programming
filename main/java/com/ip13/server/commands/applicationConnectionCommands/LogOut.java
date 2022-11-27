package com.ip13.server.commands.applicationConnectionCommands;

import com.ip13.forwardedObjects.commandsEnum.COMMANDS;
import com.ip13.forwardedObjects.request.Request;
import com.ip13.forwardedObjects.response.Response;
import com.ip13.server.collection.CollectionManager;
import com.ip13.server.commands.commandsInterface.AbstractApplicationConnectionCommand;
import com.ip13.server.dataBase.DatabaseManager;
import com.ip13.server.network.Client;
import com.ip13.server.network.User;
import com.ip13.server.users.UsersManager;

public class LogOut extends AbstractApplicationConnectionCommand {
    public LogOut(CollectionManager collectionManager, DatabaseManager databaseManager, COMMANDS commandName, UsersManager usersManager) {
        super(collectionManager, databaseManager, commandName, usersManager);
    }

    @Override
    public Response<Boolean> execute(Request<?> request) {
        boolean isRemoved = usersManager.removeUser(new User(new Client(request.getLogin(), request.getPassword()), request.getSocket()));
        return new Response<>(commandName, "You have successfully logged out", isRemoved);
    }
}
