package by.examle.ui.pages;

import com.codeborne.selenide.Selenide;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class MainPage {

    public MainPage open() {
        Selenide.open("https://reqres.in/");
        getWebDriver().manage().window().maximize();
        return new MainPage();
    }
}