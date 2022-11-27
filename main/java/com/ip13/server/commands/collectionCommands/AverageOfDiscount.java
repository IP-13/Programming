package com.ip13.server.commands.collectionCommands;

import com.ip13.forwardedObjects.commandsEnum.COMMANDS;
import com.ip13.forwardedObjects.request.Request;
import com.ip13.forwardedObjects.response.Response;
import com.ip13.server.collection.CollectionManager;
import com.ip13.server.commands.commandsInterface.AbstractCollectionCommand;
import com.ip13.server.dataBase.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AverageOfDiscount extends AbstractCollectionCommand {
    public AverageOfDiscount(CollectionManager collectionManager, DatabaseManager databaseManager, COMMANDS commandName) {
        super(collectionManager, databaseManager, commandName);
    }

    @Override
    public Response<Double> execute(Request<?> request) {
        try (PreparedStatement statement = databaseManager.getConnection().prepareStatement(
                "SELECT AVG(discount) as average_discount from tickets;"
        )) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Response<>(commandName, "Successful request", resultSet.getDouble("average_discount"));
            } else {
                return new Response<>(commandName, "Collection is empty", 0.0);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Average of discount command error", e);
        }
    }
}
