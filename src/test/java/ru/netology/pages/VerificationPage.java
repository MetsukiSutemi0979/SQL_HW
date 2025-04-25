package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;
import ru.netology.data.DataHelper;
import ru.netology.utils.DBHelper;
import java.sql.SQLException;

public class VerificationPage {
    private SelenideElement codeVerify = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("button");


    public void verify(String code) {
        codeVerify.setValue(code);
        verifyButton.click();
    }
}
