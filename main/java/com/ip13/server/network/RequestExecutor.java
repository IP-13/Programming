package com.ip13.server.network;

import com.ip13.forwardedObjects.commandsEnum.COMMANDS;
import com.ip13.forwardedObjects.request.Request;
import com.ip13.forwardedObjects.response.Response;
import com.ip13.server.collection.CollectionManager;
import com.ip13.server.commands.applicationConnectionCommands.*;
import com.ip13.server.commands.collectionCommands.*;
import com.ip13.server.commands.commandsInterface.Command;
import com.ip13.server.dataBase.DatabaseManager;
import com.ip13.server.users.UsersManager;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class RequestExecutor {
    private final CollectionManager collectionManager;
    private final ExecutorService executorService;
    private final DatabaseManager databaseManager;
    private final UsersManager usersManager;

    public RequestExecutor(CollectionManager collectionManager, ExecutorService executorService, DatabaseManager databaseManager, UsersManager usersManager) {
        this.collectionManager = collectionManager;
        this.executorService = executorService;
        this.databaseManager = databaseManager;
        this.usersManager = usersManager;
        initializeCommandMap();
    }

    public Response<?> executeRequest(Request<?> request) {
        Future<Response<?>> result = executorService.submit(() -> {
            Client client = new Client(request.getLogin(), request.getPassword());
            if (request.getCommandName().equals(COMMANDS.REGISTRATION)
                    || request.getCommandName().equals(COMMANDS.LOG_IN)
                    || usersManager.checkActiveUser(new User(client, request.getSocket()))) {
                Response<?> response = commandMap.get(request.getCommandName()).execute(request);
                response.setSocket(request.getSocket());
                response.setMessage("Request was executed by thread: " + Thread.currentThread().getName() + "\t\t" + response.getMessage());
                if (!request.getCommandName().equals(COMMANDS.HISTORY)) {
                    ((History) commandMap.get(COMMANDS.HISTORY)).addCommandToHistory(request.getCommandName());
                }
                return response;
            }
            else {
                Response<?> response = new Response<>(request.getCommandName(), "Log in, before make requests", null);
                response.setSocket(request.getSocket());
                return response;
            }
        });

        try {
            return result.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private final HashMap<COMMANDS, Command> commandMap = new HashMap<>();

    private void initializeCommandMap() {
        commandMap.put(COMMANDS.ADD, new Add(collectionManager, databaseManager, COMMANDS.ADD));
        commandMap.put(COMMANDS.ADD_IF_MAX, new AddIfMax(collectionManager, databaseManager, COMMANDS.ADD_IF_MAX));
        commandMap.put(COMMANDS.AVERAGE_OF_DISCOUNT, new AverageOfDiscount(collectionManager, databaseManager, COMMANDS.AVERAGE_OF_DISCOUNT));
        commandMap.put(COMMANDS.CLEAR, new Clear(collectionManager, databaseManager, COMMANDS.CLEAR));
        commandMap.put(COMMANDS.FILTER_BY_REFUNDABLE, new FilterByRefundable(collectionManager, databaseManager, COMMANDS.FILTER_BY_REFUNDABLE));
        commandMap.put(COMMANDS.HELP, new Help(collectionManager, databaseManager, COMMANDS.HELP));
        commandMap.put(COMMANDS.HISTORY, new History(collectionManager, databaseManager, COMMANDS.HISTORY));
        commandMap.put(COMMANDS.INFO, new Info(collectionManager, databaseManager, COMMANDS.INFO));
        commandMap.put(COMMANDS.LOG_IN, new LogIn(collectionManager, databaseManager, COMMANDS.LOG_IN, usersManager));
        commandMap.put(COMMANDS.PRINT_DESCENDING, new PrintDescending(collectionManager, databaseManager, COMMANDS.PRINT_DESCENDING));
        commandMap.put(COMMANDS.REGISTRATION, new Registration(collectionManager, databaseManager, COMMANDS.REGISTRATION, usersManager));
        commandMap.put(COMMANDS.REMOVE_BY_ID, new RemoveById(collectionManager, databaseManager, COMMANDS.REMOVE_BY_ID));
        commandMap.put(COMMANDS.REMOVE_HEAD, new RemoveHead(collectionManager, databaseManager, COMMANDS.REMOVE_HEAD));
        commandMap.put(COMMANDS.SHOW, new Show(collectionManager, databaseManager, COMMANDS.SHOW));
        commandMap.put(COMMANDS.UPDATE_BY_ID, new UpdateById(collectionManager, databaseManager, COMMANDS.UPDATE_BY_ID));
        commandMap.put(COMMANDS.LOG_OUT, new LogOut(collectionManager, databaseManager, COMMANDS.LOG_OUT, usersManager));
    }

}
