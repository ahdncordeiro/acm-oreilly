package com.andrecordeiro;

import org.jetbrains.annotations.NotNull;
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
            driver.get(OREILLY_ACM_LOGIN_URL);
            System.out.println("current url " + driver.getCurrentUrl());
    }
}
