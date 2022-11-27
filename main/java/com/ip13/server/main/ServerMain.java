package com.ip13.server.main;

import com.ip13.forwardedObjects.request.Request;
import com.ip13.forwardedObjects.response.Response;
import com.ip13.forwardedObjects.ticket.Ticket;
import com.ip13.server.collection.CollectionManager;
import com.ip13.server.dataBase.DatabaseManager;
import com.ip13.server.network.RequestExecutor;
import com.ip13.server.network.RequestReceiver;
import com.ip13.server.network.ResponseSender;
import com.ip13.server.network.User;
import com.ip13.server.users.UsersManager;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class ServerMain {

    public static void main(String[] args) {
        startServer();
    }

    private static void startServer() {
        try (DatagramSocket datagramSocket = new DatagramSocket(ServerInfo.PORT);
             Connection connection = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASSWORD)) {
            ExecutorService executorService = Executors.newCachedThreadPool();

            PriorityQueue<Ticket> collection = new PriorityQueue<>(
                    Comparator.comparingInt(Ticket::getId)
            );

            ReentrantLock reentrantLock = new ReentrantLock();

            CollectionManager collectionManager = new CollectionManager(collection, reentrantLock);

            DatabaseManager databaseManager = new DatabaseManager(connection, reentrantLock);
            databaseManager.init();

            LinkedList<User> activeUsers = new LinkedList<>();
            UsersManager usersManager = new UsersManager(reentrantLock, activeUsers);


            RequestReceiver requestReceiver = new RequestReceiver(datagramSocket);

            RequestExecutor requestExecutor = new RequestExecutor(collectionManager, executorService, databaseManager, usersManager);

            ResponseSender responseSender = new ResponseSender(datagramSocket);


            while (true) {
                Request<?> request = requestReceiver.receiveRequest();
                Response<?> response = requestExecutor.executeRequest(request);
                responseSender.sendResponse(response);
            }

        } catch (SQLException | SocketException e) {
            throw new RuntimeException(e);
        }
    }
}
