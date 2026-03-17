package tests;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CreateClientTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get("https://secure.simplepractice.com");
    }

    @Test
    public void createClientHappyPath() {

        //NOTE: Replace the credentials with valid ones for the test environment
        String username = "ANY_EMAIL";
        String password = "ANY_PASSWORD";

        //LOGIN
        By emailField = By.id("user_email");
        By passwordField = By.id("user_password");
        By signInButton = By.cssSelector("input[id='submitBtn']");

        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));

        driver.findElement(emailField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(signInButton).click();

        //WAIT FOR DASHBOARD
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        //HANDLE OPTIONAL PRIVACY POPUP
        try {
            By acceptButton = By.xpath("//button[contains(.,'Accept') or contains(.,'Aceptar')]");
            wait.until(ExpectedConditions.elementToBeClickable(acceptButton));
            driver.findElement(acceptButton).click();
            System.out.println("Privacy popup accepted.");
        } catch (Exception e1) {
            try {
                By closeButton = By.xpath("//button[contains(@aria-label,'Close') or contains(@aria-label,'Cerrar')]");
                wait.until(ExpectedConditions.elementToBeClickable(closeButton));
                driver.findElement(closeButton).click();
                System.out.println("Privacy popup closed.");
            } catch (Exception e2) {
                System.out.println("Privacy popup not present.");
            }
        }

        //OPEN "+" MENU
        By plusMenu = By.cssSelector("button[aria-label='Create']");
        wait.until(ExpectedConditions.elementToBeClickable(plusMenu));
        driver.findElement(plusMenu).click();

        //NEW CLIENT
        By newClientOption = By.xpath("//*[contains(normalize-space(.),'Create Client')]");
        wait.until(ExpectedConditions.elementToBeClickable(newClientOption));
        driver.findElement(newClientOption).click();

        //CLIENT FORM
        By firstNameField = By.xpath("//input[contains(@id,'firstName')]");
        By lastNameField = By.xpath("//input[contains(@id,'lastName')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));

        String firstName = "Test" + System.currentTimeMillis();
        String lastName = "Automation";

        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);

        //STATUS DROPDOWN
        By statusDropdown = By.cssSelector("button[id*='spds-input-dropdown']");
        wait.until(ExpectedConditions.elementToBeClickable(statusDropdown));
        driver.findElement(statusDropdown).click();

        //SELECT ACTIVE OPTION
        By activeOption = By.xpath("//button[@data-value='active']");
        wait.until(ExpectedConditions.elementToBeClickable(activeOption));
        WebElement activeButton = driver.findElement(activeOption);
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({behavior:'instant', block:'center'});",
            activeButton
        );
        activeButton.click();

        //CONTINUE BUTTON
        By continueButton = By.xpath("//button[normalize-space(.)='Continue']");
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        driver.findElement(continueButton).click();

        //CLIENTS PAGE
        By clientsMenu = By.xpath("//a[@aria-label='Clients']");
        wait.until(ExpectedConditions.elementToBeClickable(clientsMenu));
        driver.findElement(clientsMenu).click();

        //VERIFY CLIENT EXISTS
        By createdClient = By.xpath("//a[normalize-space()='" + firstName + " " + lastName + "']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(createdClient));
        WebElement client = driver.findElement(createdClient);
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({behavior:'instant', block:'center'});",
            client
        );

        boolean clientFound = client.isDisplayed();
        Assert.assertTrue(clientFound, "Client was not found in Clients list");
        System.out.println("Client successfully verified: " + firstName + " " + lastName);
    }

    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
