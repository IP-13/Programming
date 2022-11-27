package com.ip13.server.network;

import com.ip13.forwardedObjects.response.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ResponseSender {
    private final DatagramSocket datagramSocket;

    public ResponseSender(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    public void sendResponse(Response<?> response) {
        new Thread(() -> {
            try {
                response.setMessage("Response was sent by thread: " + Thread.currentThread().getName() + "\t\t" + response.getMessage());
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(response);
                InetAddress address = response.getSocket().getAddress();
                int port = response.getSocket().getPort();
                DatagramPacket datagramPacket = new DatagramPacket(byteArrayOutputStream.toByteArray(), byteArrayOutputStream.size(), address, port);
                datagramSocket.send(datagramPacket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }


}
