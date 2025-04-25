package ru.netology.utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.*;

public class DBHelper {
    private static final String URL = System.getProperty("jdbc.url",
            System.getenv().getOrDefault("DB_URL",
                    "jdbc:mysql://localhost:3306/homework"));
    private static final String USER = System.getProperty("jdbc.user",
            System.getenv().getOrDefault("DB_USER","homework"));
    private static final String PASSWORD = System.getProperty("jdbc.password",
            System.getenv().getOrDefault("DB_PASS","pass"));

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void execute(String sql, Object... params) throws SQLException {
        try (var conn = getConnection()) {
            new QueryRunner().update(conn, sql, params);
        }
    }

    public static void clearData() throws SQLException {
        execute("DELETE FROM auth_codes;");
        execute("DELETE FROM cards");
        execute("DELETE FROM users;");
    }

    public static <T> T querySingle(String sql, ScalarHandler<T> handler, Object... params) throws SQLException {
        try (var conn = getConnection()) {
            return new QueryRunner().query(conn, sql, handler, params);
        }
    }

    public static String getLatestCode(String login) throws SQLException {
        String code = null;
        String sql = """
            SELECT ac.code
            FROM auth_codes ac
            JOIN users u ON ac.user_id = u.id
            WHERE u.login = ?
            ORDER BY ac.created DESC
            LIMIT 1
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    code = rs.getString("code");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return code;
    }
}

