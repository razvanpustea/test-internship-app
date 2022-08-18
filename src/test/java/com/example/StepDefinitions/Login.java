package com.example.StepDefinitions;

import com.example.helpers.SeleniumInitializer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class Login {

    private final WebDriver driver;

    public Login() {
        driver = SeleniumInitializer.getDriver();
    }

    @Given("^user is on login page$")
    public void navigateToLoginPage() {
        driver.get("http://localhost:4200/");

        WebElement loginButton = driver.findElement(By.xpath("/html/body/app-root/app-home/div/div/div[2]/div[3]/div/form/div[3]/div[2]/button"));

        Assert.assertEquals(loginButton.getText(), "Login");
    }

    @When("^user enters valid username and password$")
    public void insertValidLoginCredentials() {
        final String insertedUsername = "validUsername";
        final String insertedPassword = "validPassword";

        WebElement usernameField = driver.findElement(By.xpath("/html/body/app-root/app-home/div/div/div[2]/div[3]/div/form/div[1]/input"));
        WebElement passwordField = driver.findElement(By.xpath("/html/body/app-root/app-home/div/div/div[2]/div[3]/div/form/div[2]/input"));
        usernameField.sendKeys(insertedUsername);
        passwordField.sendKeys(insertedPassword);

        Assert.assertEquals(usernameField.getAttribute("value"), insertedUsername);
        Assert.assertEquals(passwordField.getAttribute("value"), insertedPassword);
    }

    @When("^presses the Login button$")
    public void pressLoginButton() {
        WebElement loginButton = driver.findElement(By.tagName("button"));
        loginButton.click();
    }

    @Then("^user should log in and be redirected to homepage$")
    public void logInSuccessfully() {
        final String homePage = "http://localhost:4200/users";
        Assert.assertEquals(driver.getCurrentUrl(), homePage);
        driver.quit();
    }
}
