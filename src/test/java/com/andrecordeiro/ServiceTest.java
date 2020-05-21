package com.andrecordeiro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.apache.commons.lang3.reflect.MethodUtils.invokeMethod;
import static org.mockito.Mockito.*;

class ServiceTest {
    private WebDriver driver;
    private Service service;

    @BeforeEach
    void beforeEach() {
        this.driver = Mockito.mock(WebDriver.class);
        this.service = new Service("user","password", driver);
    }

    @Test
    void shouldGoToAcmLoginPage() throws Exception {
        when(driver.getCurrentUrl()).thenReturn("https://go.oreilly.com/acm");

        invokeMethod(this.service, true, "goToAcmPage");

        verify(driver, times(1)).get("https://go.oreilly.com/acm");
    }

    @Test
    void shouldFillUsernameAndPasswordFields() throws Exception {
        var username = "user";
        var password = "password";
        var usernameWebElement = mock(WebElement.class);
        var passwordWebElement = mock(WebElement.class);
        when(driver.findElement(By.id("username"))).thenReturn(usernameWebElement);
        when(driver.findElement(By.id("pword"))).thenReturn(passwordWebElement);

        invokeMethod(new Service(username, password, driver), true, "fillUsernameAndPasswordFields");

        verify(driver).findElement(By.id("username"));
        verify(driver).findElement(By.id("pword"));
        verify(usernameWebElement).sendKeys(username);
        verify(passwordWebElement).sendKeys(password);
    }

    @Test
    void shouldSubmitLoginSuccessfully() throws Exception {
        var signInButtonWebElement = mock(WebElement.class);
        when(driver.findElement(By.name("_eventId_proceed"))).thenReturn(signInButtonWebElement);

        invokeMethod(this.service, true, "signIn");

        verify(driver).findElement(By.name("_eventId_proceed"));
        verify(signInButtonWebElement).click();
    }
}
