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

public class LogIn extends AbstractApplicationConnectionCommand {
    public LogIn(CollectionManager collectionManager, DatabaseManager databaseManager, COMMANDS commandName, UsersManager usersManager) {
        super(collectionManager, databaseManager, commandName, usersManager);
    }

    @Override
    public Response<Boolean> execute(Request<?> request) {
        Client client = new Client(request.getLogin(), request.getPassword());
        String message;
        boolean data;

        if (databaseManager.checkClient(client)) {
            message = "You have successfully logged in";
            data = true;
            usersManager.addActiveUser(new User(client, request.getSocket()));
        } else if (!databaseManager.checkClient(client) && databaseManager.checkLogin(client.getLogin())) {
            message = "Wrong password";
            data = false;
        } else {
            message = "No such user";
            data = false;
        }

        return new Response<>(commandName, message, data);
    }


}
