package com.andrecordeiro;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Service {

    private static final String OREILLY_ACM_LOGIN_URL = "https://go.oreilly.com/acm";

    private String acm_username;
    private String acm_password;
    private WebDriver driver;

    public Service(@NotNull String acm_username, @NotNull String acm_password, @NotNull WebDriver driver) {
        this.acm_username = acm_username;
        this.acm_password = acm_password;
        this.driver = driver;
    }

    public void process() {
        goToAcmPage();
        fillUsernameAndPasswordFields();
    }

    private void fillUsernameAndPasswordFields() {
        driver.findElement(By.id("username")).sendKeys(acm_username);
        driver.findElement(By.id("pword")).sendKeys(acm_password);
    }

    private void goToAcmPage() {
        driver.get(OREILLY_ACM_LOGIN_URL);
    }

}
