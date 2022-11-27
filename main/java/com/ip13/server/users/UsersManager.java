package com.ip13.server.users;

import com.ip13.server.network.User;

import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class UsersManager {
    private final ReentrantLock reentrantLock;
    private final LinkedList<User> activeUsers;

    public UsersManager(ReentrantLock reentrantLock, LinkedList<User> activeUsers) {
        this.reentrantLock = reentrantLock;
        this.activeUsers = activeUsers;
    }

    public void addActiveUser(User user) {
        try {
            reentrantLock.lock();
            activeUsers.add(user);
        } finally {
            reentrantLock.unlock();
        }
    }

    public boolean removeUser(User user) {
        try {
            reentrantLock.lock();
            return activeUsers.remove(user);
        } finally {
            reentrantLock.unlock();
        }
    }

    public static void notifyAllUsers() {

    }

    public boolean checkActiveUser(User user) {
        return activeUsers.contains(user);
    }

}
