package ru.netology.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import lombok.SneakyThrows;
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

    @AfterAll
    static void cleanup() throws SQLException {
        DBHelper.clearData();
    }

    @Test
    void testLogin() throws SQLException {
        open("http://localhost:9999");
        AuthInfo authInfo = DataHelper.getValidAuthInfo();
        LoginPage loginPage = new LoginPage();
        loginPage.validLogin(authInfo);
        VerificationPage verificationPage = new VerificationPage();
        String code = DataHelper.getVerificationCode(authInfo);
        verificationPage.verify(code);
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.shouldBeVisible();
    }
}
