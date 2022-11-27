package com.ip13.server.network;

import com.ip13.forwardedObjects.network.ClientSocket;
import com.ip13.forwardedObjects.request.Request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class RequestReceiver {
    private final DatagramSocket datagramSocket;

    public RequestReceiver(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }

    public Request<?> receiveRequest() {
        try {
            byte[] bytes = new byte[4000];
            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
            datagramSocket.receive(datagramPacket);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Request<?> request = (Request<?>) objectInputStream.readObject();
            ClientSocket clientSocket = new ClientSocket(datagramPacket.getAddress(), datagramPacket.getPort());
            request.setSocket(clientSocket);
            return request;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Cannot read request");
    }

}
