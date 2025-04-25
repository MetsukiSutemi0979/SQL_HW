package ru.netology.data;

public class AuthInfo {
    private static String login = "";
    private final String password;

    public AuthInfo(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}

