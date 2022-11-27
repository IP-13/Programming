package com.ip13.forwardedObjects.network;

import java.io.Serial;
import java.io.Serializable;
import java.net.InetAddress;

public class ClientSocket implements Serializable {
    private final InetAddress address;
    private final int port;

    @Serial
    private static final long serialVersionUID = 3404500;

    public ClientSocket(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}
