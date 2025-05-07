package ru.netology.data;

import lombok.Value;

@Value
public class AuthInfo {
    private static String login = "";
    private static String password;

    public AuthInfo(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static String getLogin() {
        return login;
    }

    public static String getPassword(){
        return password;
    }
}

