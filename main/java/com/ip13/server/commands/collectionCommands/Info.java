package com.ip13.server.commands.collectionCommands;

import com.ip13.forwardedObjects.commandsEnum.COMMANDS;
import com.ip13.forwardedObjects.request.Request;
import com.ip13.forwardedObjects.response.Response;
import com.ip13.server.collection.CollectionManager;
import com.ip13.server.commands.commandsInterface.AbstractCollectionCommand;
import com.ip13.server.dataBase.DatabaseManager;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

public class Info extends AbstractCollectionCommand {
    public Info(CollectionManager collectionManager, DatabaseManager databaseManager, COMMANDS commandName) {
        super(collectionManager, databaseManager, commandName);
    }

    @Override
    public Response<String> execute(Request<?> request) {
        int size = collectionManager.getCollection().size();
        LocalDateTime creationTime = collectionManager.getCreationTime();
        AtomicReference<Double> sum = new AtomicReference<>(0.0);
        collectionManager.getCollection().forEach(ticket -> {
            sum.updateAndGet(v -> v + ticket.getPrice());
        });
        String info = "Size: " + size + " Creation time: " + creationTime + " Total price: " + sum;
        return new Response<>(commandName, "Information about collection", info);
    }

}
