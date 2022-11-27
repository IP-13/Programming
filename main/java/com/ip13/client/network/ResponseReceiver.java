package com.ip13.client.network;

import com.ip13.forwardedObjects.response.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ResponseReceiver {
    private final DatagramChannel datagramChannel;

    public ResponseReceiver(DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
    }

    public Response<?> receiveResponse() {
        try {
            byte[] bytes = new byte[4000];
            datagramChannel.receive(ByteBuffer.wrap(bytes));
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (Response<?>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
