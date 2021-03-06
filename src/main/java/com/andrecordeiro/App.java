package com.andrecordeiro;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static java.lang.System.getenv;

public class App {

    public static void main(String[] args) {
        var acm_username = getenv("ACM_USERNAME");
        var acm_password = getenv("ACM_PASSWORD");

        var webDriver = getWebDriver();

        try {
            new Service(acm_username, acm_password, webDriver).process();
        } finally {
            webDriver.quit();
        }
    }

    private static FirefoxDriver getWebDriver() {
        var options = new FirefoxOptions();
        options.setHeadless(true);
        return new FirefoxDriver(options);
    }
}
