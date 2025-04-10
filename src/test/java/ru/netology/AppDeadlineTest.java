package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AppDeadlineTest {

    private static Connection connection;
    private final String BASE_URL = "http://localhost:9999";
    private final String TEST_USER = "vasya";
    private final String TEST_PASSWORD = "qwerty123";

    private String getLastAuthCode(String userLogin) throws SQLException {
        String query = """
        SELECT code 
        FROM auth_codes 
        WHERE user_id = (
            SELECT id 
            FROM users 
            WHERE login = ?
        ) 
        ORDER BY created DESC 
        LIMIT 1""";
        return query;
    }


    @BeforeAll
    static void setupAll() {
        Configuration.browser = "chrome";
        Configuration.timeout = 15000;
    }

    @BeforeAll
    static void setUpDB() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/homework", "homework", "pass");
    }

    @BeforeEach
    void setup() {
        Configuration.browserCapabilities = new ChromeOptions().setBrowserVersion("115");
    }

    @BeforeEach
    void clearData() throws SQLException {
        setupDatabaseConnection();
        clearTestData();
        Selenide.open(BASE_URL);
    }

    @AfterAll
    static void tearDownDB() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    void shouldLoginWithValidDB() throws SQLException {
        $("[data-test-id=login] input").setValue(TEST_USER);
        $("[data-test-id=password] input").setValue(TEST_PASSWORD);
        $("[data-test-id=action-login]").click();

        String code = getLastAuthCode(TEST_USER);

        $("[data-test-id=code] input")
                .shouldBe(Condition.visible)
                .setValue(code);

        $("[data-test-id=action-verify]").click();

        $("[data-test-id=dashboard]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Личный кабинет"));
    }
}
