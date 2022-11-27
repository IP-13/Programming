package com.ip13.forwardedObjects.response;

import com.ip13.forwardedObjects.commandsEnum.COMMANDS;
import com.ip13.forwardedObjects.network.ClientSocket;

import java.io.Serial;
import java.io.Serializable;

public class Response<T> implements Serializable {
    private final COMMANDS commandName;
    private String message;
    private final T data;
    private ClientSocket socket;

    @Serial
    private static final long serialVersionUID = 3404502;

    public Response(COMMANDS commandName, String message, T data) {
        this.commandName = commandName;
        this.message = message;
        this.data = data;
    }

    public COMMANDS getCommandName() {
        return commandName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public ClientSocket getSocket() {
        return socket;
    }

    public void setSocket(ClientSocket socket) {
        this.socket = socket;
    }

}
