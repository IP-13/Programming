package com.ip13.client.test;

import com.ip13.client.network.RequestSender;
import com.ip13.client.network.ResponseHandler;
import com.ip13.client.network.ResponseReceiver;
import com.ip13.forwardedObjects.commandsEnum.COMMANDS;
import com.ip13.forwardedObjects.request.Request;
import com.ip13.forwardedObjects.ticket.Ticket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class ClientTestMain {
    private static int port = 1919;

    public static void main(String[] args) {
        if (args.length == 0) {
            userTest();
        } else {
            infiniteTest(args[0], args[1], Integer.parseInt(args[2]));
        }
    }

    private static void userTest() {
        try (DatagramChannel datagramChannel = DatagramChannel.open()) {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(), port);
            RequestSender requestSender = new RequestSender(datagramChannel, inetSocketAddress);
            ResponseReceiver responseReceiver = new ResponseReceiver(datagramChannel);
            ResponseHandler responseHandler = new ResponseHandler();
            UserRequestReader userRequestReader = new UserRequestReader(new Scanner(System.in));
            while (true) {
                Request<?> request = userRequestReader.readRequest();
                requestSender.sendRequest(request);
                responseHandler.handleResponse(responseReceiver.receiveResponse());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void commandTest(String login, String password) {
        try (DatagramChannel datagramChannel = DatagramChannel.open()) {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(), port);
            RequestSender requestSender = new RequestSender(datagramChannel, inetSocketAddress);
            ResponseReceiver responseReceiver = new ResponseReceiver(datagramChannel);
            ResponseHandler responseHandler = new ResponseHandler();
            RequestFactory requestFactory = new RequestFactory(login, password);


            Request<String> request1 = new Request<>(login, password, password, COMMANDS.REGISTRATION);
            requestSender.sendRequest(request1);
            responseHandler.handleResponse(responseReceiver.receiveResponse());


            Request<Ticket> request2 = new Request<>(login, password, requestFactory.randomTicket(), COMMANDS.ADD);
            requestSender.sendRequest(request2);
            responseHandler.handleResponse(responseReceiver.receiveResponse());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void infiniteTest(String login, String password, int sleepTime) {
        try (DatagramChannel datagramChannel = DatagramChannel.open()) {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(), port);
            RequestSender requestSender = new RequestSender(datagramChannel, inetSocketAddress);
            ResponseReceiver responseReceiver = new ResponseReceiver(datagramChannel);
            ResponseHandler responseHandler = new ResponseHandler();
            RequestFactory requestFactory = new RequestFactory(login, password);

            requestSender.sendRequest(new Request<>(login, password, password, COMMANDS.REGISTRATION));
            responseHandler.handleResponse(responseReceiver.receiveResponse());

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            requestSender.sendRequest(new Request<>(login, password, true, COMMANDS.LOG_IN));
            responseHandler.handleResponse(responseReceiver.receiveResponse());

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            while (true) {
                Request<?> request = requestFactory.createRequest();
                System.out.println(request.getCommandName());

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                requestSender.sendRequest(request);
                responseHandler.handleResponse(responseReceiver.receiveResponse());

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
