package com.casino.game;

public class TransactionsRequest {
    /**
     * {
     *     username:"dineo",
     *     password: "swordfish"
     * }
     */
    public String username;
    public String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "TransactionsRequest{" +
            "username='" + username + '\'' +
            ", password='" + password + '\'' +
            '}';
    }
}
