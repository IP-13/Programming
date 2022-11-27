package com.ip13.client.network;

import com.ip13.forwardedObjects.request.Request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class RequestSender {
    private final DatagramChannel datagramChannel;
    private final InetSocketAddress inetSocketAddress;

    public RequestSender(DatagramChannel datagramChannel, InetSocketAddress inetSocketAddress) {
        this.datagramChannel = datagramChannel;
        this.inetSocketAddress = inetSocketAddress;
    }

    public void sendRequest(Request<?> request) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(request);
            datagramChannel.send(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()), inetSocketAddress);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
