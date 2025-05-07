package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.AuthInfo;

import static com.codeborne.selenide.Selenide.*;


public class LoginPage {
    private SelenideElement login = $("[data-test-id=login] input");
    private SelenideElement password = $("[data-test-id=password] input");
    private SelenideElement submitButton  = $("button");

    public VerificationPage validLogin(AuthInfo authInfo) {
        login.setValue(authInfo.getLogin()); // или authInfo.login()
        password.setValue(authInfo.getPassword());
        submitButton.click();
        return new VerificationPage();
    }
}

