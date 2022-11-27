package com.ip13.server.commands.applicationConnectionCommands;

import com.ip13.forwardedObjects.commandsEnum.COMMANDS;
import com.ip13.forwardedObjects.request.Request;
import com.ip13.forwardedObjects.response.Response;
import com.ip13.server.collection.CollectionManager;
import com.ip13.server.commands.commandsInterface.AbstractApplicationConnectionCommand;
import com.ip13.server.dataBase.DatabaseManager;
import com.ip13.server.network.Client;
import com.ip13.server.users.UsersManager;

public class Registration extends AbstractApplicationConnectionCommand {
    public Registration(CollectionManager collectionManager, DatabaseManager databaseManager, COMMANDS commandName, UsersManager usersManager) {
        super(collectionManager, databaseManager, commandName, usersManager);
    }

    @Override
    public Response<Boolean> execute(Request<?> request) {
        Client client = new Client(request.getLogin(), request.getPassword());
        String message;
        boolean data;

        boolean checkLogin = databaseManager.checkLogin(client.getLogin());
        boolean checkRepeatedPassword = client.getPassword().equals(request.getData());

        if (!checkLogin && checkRepeatedPassword) {
            message = "Successful registration";
            data = true;
            databaseManager.addClient(client);
        } else if (checkLogin) {
            message = "Login \"" + request.getLogin() + "\" already in use";
            data = false;
        } else {
            message = "Passwords do not match";
            data = false;
        }

        return new Response<>(commandName, message, data);
    }

}
