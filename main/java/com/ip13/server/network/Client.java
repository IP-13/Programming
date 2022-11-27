package com.ip13.server.network;

public class Client {
    private final String login;
    private final String password;

    public Client(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Client && this.login.equals(((Client) obj).getLogin()) && this.password.equals(((Client) obj).getPassword());
    }

}
