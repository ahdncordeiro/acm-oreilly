package com.andrecordeiro;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;

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
        var webElement = mock(WebElement.class);
        when(driver.findElement(By.className("sub-title"))).thenReturn(webElement);
        when(webElement.getAttribute("value")).thenReturn("ACM Account");
        when(driver.findElement(By.id("username"))).thenReturn(mock(WebElement.class));
        when(driver.findElement(By.id("pword"))).thenReturn(mock(WebElement.class));
        when(driver.findElement(By.name("_eventId_proceed"))).thenReturn(mock(WebElement.class));

        invokeMethod(this.service, true, "goToAcmPage");

        verify(driver).get("https://go.oreilly.com/acm");
        verify(driver, times(2)).findElement(By.className("sub-title"));
        verify(driver).findElement(By.id("username"));
        verify(driver).findElement(By.id("pword"));
        verify(driver).findElement(By.name("_eventId_proceed"));
    }

    @Test
    void shouldGoToAcmLoginPageFailsForNotFindingElement() throws Exception {

        try {
            invokeMethod(this.service, true, "goToAcmPage");
            Assertions.fail("Should have thrown an exception");

        } catch (InvocationTargetException e) {
            var targetException = e.getTargetException();
            Assertions.assertEquals(targetException.getClass(), TimeoutException.class);
            Assertions.assertTrue(targetException.getMessage().contains("Campos necessários presentes para o sign in não estão visíveis"), "Mensagem de erro errada");
            verify(driver).get("https://go.oreilly.com/acm");
            verify(driver, atLeastOnce()).findElement(By.className("sub-title"));
        }
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
        when(driver.getCurrentUrl()).thenReturn("learning.oreilly.com");

        invokeMethod(this.service, true, "signIn");

        verify(driver).findElement(By.name("_eventId_proceed"));
        verify(signInButtonWebElement).click();
        verify(driver, atLeastOnce()).getCurrentUrl();
    }

    @Test
    void shouldSubmitLoginFailsAfterTimeout() throws Exception {
        var signInButtonWebElement = mock(WebElement.class);
        when(driver.findElement(By.name("_eventId_proceed"))).thenReturn(signInButtonWebElement);

        try {
            invokeMethod(this.service, true, "signIn");
            Assertions.fail("Should have thrown an exception");

        } catch (InvocationTargetException e) {
            var targetException = e.getTargetException();
            Assertions.assertEquals(targetException.getClass(), TimeoutException.class);
            Assertions.assertTrue(targetException.getMessage().contains("Página não foi redirecionada para learning o'reilly"), "Mensagem de erro errada");
            verify(driver).findElement(By.name("_eventId_proceed"));
            verify(driver, atLeastOnce()).getCurrentUrl();
        }
    }
}
