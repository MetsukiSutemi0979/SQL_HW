package ru.netology.pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Condition.visible;

public class DashboardPage {
    public void shouldBeVisible() {
        $("[data-test-id=dashboard]").shouldBe(visible);
    }
}
