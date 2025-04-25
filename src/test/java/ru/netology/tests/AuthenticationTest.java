package ru.netology.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.netology.data.AuthInfo;
import ru.netology.data.DataHelper;
import ru.netology.utils.DBHelper;
import ru.netology.pages.LoginPage;
import ru.netology.pages.VerificationPage;
import ru.netology.pages.DashboardPage;

import java.sql.DriverManager;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;


public class AuthenticationTest {
    @BeforeAll
    static void setupAll() {
        Configuration.browser = "chrome";
        Configuration.timeout = 15000;
    }

    @BeforeEach
    void setup() throws SQLException {
        Configuration.browserCapabilities = new ChromeOptions().setBrowserVersion("115");
    }

    @AfterEach
    void cleanup() throws SQLException {
        DBHelper.clearData();
    }

    @Test
    void testLogin() throws SQLException {
        open("http://localhost:9999");
        AuthInfo authInfo = DataHelper.getValidAuthInfo();
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = new VerificationPage();
        loginPage.validLogin(authInfo);
        loginPage.checkPassword(authInfo);
        String code = DataHelper.getVerificationCode(authInfo);
        verificationPage.verify(code);
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.shouldBeVisible();
    }
}
