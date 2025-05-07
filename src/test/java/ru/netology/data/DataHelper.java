package ru.netology.data;

import ru.netology.utils.DBHelper;

import java.sql.SQLException;
import java.util.Random;

public class DataHelper {
    private DataHelper() {}

    public static AuthInfo getValidAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static String getVerificationCode(AuthInfo authInfo) throws SQLException {
        return DBHelper.getLatestCode(authInfo.getLogin()); // или authInfo.login()
    }
}