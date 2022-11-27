package com.ip13.server.network;

import com.ip13.forwardedObjects.network.ClientSocket;

public class User {
    private final Client client;
    private final ClientSocket clientSocket;

    public User(Client client, ClientSocket clientSocket) {
        this.client = client;
        this.clientSocket = clientSocket;
    }

    public Client getClient() {
        return client;
    }

    public ClientSocket getClientSocket() {
        return clientSocket;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User otherUser)) {
            return false;
        }

        return client.getLogin().equals(otherUser.getClient().getLogin()) &&
                client.getPassword().equals(otherUser.getClient().getPassword()) &&
                clientSocket.getPort() == otherUser.getClientSocket().getPort() &&
                clientSocket.getAddress().equals(otherUser.getClientSocket().getAddress());
    }
}
