package com.ip13.forwardedObjects.request;

import com.ip13.forwardedObjects.commandsEnum.COMMANDS;
import com.ip13.forwardedObjects.network.ClientSocket;

import java.io.Serial;
import java.io.Serializable;

public class Request<T> implements Serializable {
    private final COMMANDS commandName;
    private final String login;
    private final String password;
    private final T data;
    private ClientSocket socket;

    @Serial
    private static final long serialVersionUID = 3404501;

    public Request(String login, String password, T data, COMMANDS commandName) {
        this.login = login;
        this.password = password;
        this.data = data;
        this.commandName = commandName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public T getData() {
        return data;
    }

    public COMMANDS getCommandName() {
        return commandName;
    }

    public ClientSocket getSocket() {
        return socket;
    }

    public void setSocket(ClientSocket socket) {
        this.socket = socket;
    }
}
