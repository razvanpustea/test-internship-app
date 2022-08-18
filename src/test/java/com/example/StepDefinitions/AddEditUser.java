package com.example.StepDefinitions;

import com.example.helpers.Generator;
import com.example.helpers.SeleniumInitializer;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class AddEditUser {

    private final WebDriver driver;
    private String insertedFullName;
    private final List<String> fullNamesToBeDeleted;

    public AddEditUser() {
        driver = SeleniumInitializer.getDriver();
        fullNamesToBeDeleted = new ArrayList<>(4);
    }

    @Given("user is on homepage")
    public void navigateToHomepage() {
        final String homepageUrl = "http://localhost:4200/users";
        driver.get(homepageUrl);
    }

    @When("^user clicks on \".+\"$")
    public void clickOnAddUser() {
        SeleniumInitializer.sleep();
        driver
                .findElement(By.xpath("/html/body/app-root/app-users/app-header/mat-toolbar/button[2]/span[1]/span"))
                .click();
    }

    @And("enters valid username")
    public void insertValidUsername() {
        WebElement usernameField = driver.findElement(By.xpath("//input[@formcontrolname='username']"));
        usernameField.clear();
        usernameField.sendKeys(Generator.generateRandomName());
        SeleniumInitializer.sleep();
    }

    @And("enters valid email")
    public void insertValidEmail() {
        WebElement emailField = driver.findElement(By.xpath("//input[@formcontrolname='email']"));
        emailField.clear();
        emailField.sendKeys(Generator.generateRandomEmail());
        SeleniumInitializer.sleep();
    }

    @And("enters valid full name")
    public void insertValidFullName() {
        insertedFullName = Generator.generateRandomName();
        WebElement fullNameField = driver.findElement(By.xpath("//input[@formcontrolname='fullName']"));
        fullNameField.clear();
        fullNameField.sendKeys(insertedFullName);
        SeleniumInitializer.sleep();
    }

    @And("enters valid password")
    public void insertValidPassword() {
        WebElement passwordField = driver.findElement(By.xpath("//input[@formcontrolname='password']"));
        passwordField.clear();
        passwordField.sendKeys(Generator.generateRandomName());
        SeleniumInitializer.sleep();
    }

    @And("selects 1 or more traits")
    public void selectTraits() {
        List<WebElement> listOfCheckboxes = driver.findElements(By.xpath("//span[@class='mat-checkbox-inner-container']"));
        if (listOfCheckboxes.size() > 0)
            for (WebElement checkbox : listOfCheckboxes)
                if (checkbox.isSelected()) checkbox.click();

        int firstRandomDigit = Generator.generateRandomDigit(0, 3);
        listOfCheckboxes.get(firstRandomDigit).click();
        SeleniumInitializer.sleep();

        int secondRandomDigit;
        do
            secondRandomDigit = Generator.generateRandomDigit(0, 3);
        while (secondRandomDigit == firstRandomDigit);

        listOfCheckboxes.get(secondRandomDigit).click();
        SeleniumInitializer.sleep();
    }

    @And("selects gender")
    public void selectGender() {
        int randomDigit = Generator.generateRandomDigit(2, 4); // the parent of the parent tag that we're interested in (XPath) has a for attribute with the value: mat-radio-N-input, N is between 2 and 4 inclusively
        driver
                .findElement(By.xpath("//span[@class='mat-radio-inner-circle']//parent::span[@class='mat-radio-container']//parent::label[@for='mat-radio-" + randomDigit + "-input']"))
                .click();
        SeleniumInitializer.sleep();
    }

    @And("^clicks on Submit button from Add User page$")
    public void clickOnSubmitFromAddPage() {
        driver
                .findElement(By.xpath("/html/body/app-root/app-addmodal/div/div/form/button"))
                .click();
    }

    @Then("a new user should be added")
    public void userAdded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3L));
        wait.until(ExpectedConditions.urlMatches("http://localhost:4200/users"));

        String s = driver.getPageSource();
        Assert.assertTrue(s.contains(insertedFullName));

        driver.quit();
    }

    @Given("^clicks on (.+)'s Edit button$")
    public void clickOnEditButton(String memberFullName) throws InterruptedException {
        driver
                .findElement(By.xpath("//h1[text()=" + "'" + memberFullName + "']" + "//parent::a//parent::div//parent::div//div[@class='forButtons']//button[text()='Edit']"))
                .click();
        Thread.sleep(3000L);
    }

    @When("clicks on Submit button from Edit User page")
    public void clickOnSubmitFromEditPage() {
        driver
                .findElement(By.xpath("/html/body/app-root/app-editmodal/div/div/form/button/span[1]"))
                .click();

        driver.quit();
    }

    @When("^clicks on Delete buttons of \"([0-9A-Za-z\\s,-]+)\"")
    public void clickOnDeleteButtons(String fullNamesToBeSeparated) {
        String[] fullNames = fullNamesToBeSeparated.split(",");

        for (String fullName : fullNames) {
            fullName = fullName.trim();
            fullNamesToBeDeleted.add(fullName);

            driver
                    .findElement(By.xpath("//h1[text()='" + fullName + "']//parent::a//parent::div//parent::div//div[@class='forButtons']//button[text()='Delete']"))
                    .click();
            SeleniumInitializer.sleep();

            driver
                    .findElement(By.xpath("/html/body/app-root/app-users/div[2]/app-deletemodal/div/div/button[2]"))
                    .click();
            SeleniumInitializer.sleep();
        }
    }

    @Then("the users should be deleted")
    public void usersDeleted() {
        boolean usersDeleted = true;
        List<WebElement> fullNamesFromHomePage = driver.findElements(By.tagName("h1"));

        for (String fullName : fullNamesToBeDeleted) {
            if (usersDeleted) {
                for (WebElement fullNameFromHomePage : fullNamesFromHomePage) {
                    if (fullName.equals(fullNameFromHomePage.getText())) {
                        usersDeleted = false;
                        break;
                    }
                }
            }
        }

        Assert.assertTrue(usersDeleted, "The users were deleted successfully!");
    }
}