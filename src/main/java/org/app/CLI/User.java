package org.app.CLI;

import java.util.ArrayList;

public class User {
    private final String Username;
    private final String Password;
    private final Wallet wallet;
    private static ArrayList<User> users = new ArrayList<User>();


    public static User getUserByUsername(String username){
        for (User user : users) {
            if (user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }
    public static boolean checkUsernameExists(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)){
                System.out.println("User exists.");
                return true;
            }
        }
        return false;
    }
    public User(String username, String password) {
        Username = username;
        Password = password;
        users.add(this);
        this.wallet = new Wallet();
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public Wallet getWallet() {
        return wallet;
    }

}
