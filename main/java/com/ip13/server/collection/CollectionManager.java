package com.ip13.server.collection;

import com.ip13.forwardedObjects.ticket.Ticket;

import java.time.LocalDateTime;
import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;

public class CollectionManager {
    private final PriorityQueue<Ticket> collection;
    private final LocalDateTime creationTime;
    private final ReentrantLock reentrantLock;

    public CollectionManager(PriorityQueue<Ticket> collection, ReentrantLock reentrantLock) {
        this.collection = collection;
        this.creationTime = LocalDateTime.now();
        this.reentrantLock = reentrantLock;
    }

    public PriorityQueue<Ticket> getCollection() {
        return collection;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public ReentrantLock getReentrantLock() {
        return reentrantLock;
    }

    public boolean addTicket(Ticket ticket) {
        try {
            reentrantLock.lock();
            return collection.add(ticket);
        } finally {
            reentrantLock.unlock();
        }
    }

    public void clearCollection() {
        collection.clear();
    }

}
