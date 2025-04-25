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
        login.setValue(authInfo.getLogin());
        password.setValue(authInfo.getPassword());
        submitButton.click();
        return new VerificationPage();
    }

    public VerificationPage checkPassword(AuthInfo authInfo) {
        password.shouldHave(Condition.exactText("qwerty123"));
        return new VerificationPage();
    }
}

