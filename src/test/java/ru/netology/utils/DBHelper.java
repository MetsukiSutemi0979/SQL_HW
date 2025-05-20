package ru.netology.utils;

import com.github.javafaker.Faker;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.*;

public class DBHelper {
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/homework", "homework", "pass");
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

    public static String getLatestCode(String login) throws SQLException {
        String sql = """
                    SELECT ac.code
                    FROM auth_codes ac
                    JOIN users u ON ac.user_id = u.id
                    WHERE u.login = ?
                    ORDER BY ac.created DESC
                    LIMIT 1
                """;

        QueryRunner qr = new QueryRunner();
        ResultSetHandler<String> handler = new ScalarHandler<>();

        try (Connection conn = getConnection()) {
            return qr.query(
                    conn,
                    sql,
                    handler,
                    login
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setUp() throws SQLException {
        var faker = new Faker();
        var runner = new QueryRunner();
        var dataSQL = "INSERT INTO users(login, password) VALUES (?, ?);";
        try (var conn = getConnection()){
            runner.update(conn,dataSQL, faker.name().username(), "pass");
            runner.update(conn,dataSQL, faker.name().username(), "pass");
        }
    }
}

