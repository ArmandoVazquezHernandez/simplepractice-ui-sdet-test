package tests;

import java.time.Duration;
import org.openqa.selenium.By;
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
        DesiredCapabilities dcap = new DesiredCapabilities();
        dcap.setCapability(capabilityName:"pageLoadStrategy",value: "normal");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get("https://secure.simplepractice.com");
    }


    @Test
    public void createClientHappyPath() {

        //NOTE:Replace the credentials with any valid data
        String username = "ANY_EMAIL";
        String password = "ANY_PASSWORD";


        //LOGIN
        By emailField = By.id("user_email");
        By passwordField = By.id("user_password");
        By singinButton = By.cssSelector(cssSelector: "input[id='submitBtn']");

        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));

        driver.findElement(emailField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(singinButton).click();


        //WAIT FOR DASHBOARD
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));


        //OPEN "+" MENU
        By plusMenu = By.cssSelector(cssSelector:"button[aria-label='create']");
        wait.until(ExpectedConditions.elementToBeClickable(plusMenu));
        driver.findElement(plusMenu).click();
        try{
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }    

        //NEW CLIENT
        By newClientOption = By.xpath(xpathExpression:"//input[contains(normalize-space(.),'Create Client')]");
        wait.until(ExpectedConditions.elementToBeClickable(newClientOption));
        driver.findElement(newClientOption).click();


        //CLIENT FORM
        By firstNameField = By.xpath(xpathExpression:"//input[contains(@id,'firstName')]");
        By lastNameField = By.xpath(xpathExpression:"//input[contains(@id,'lastName')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));

        String firstName = "Test" + System.currentTimeMillis();
        String lastName = "Automation";

        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        By statusDropdown = By.cssSelector(cssSelector:"button[id*='spds-input-dropdown']");
        wait.until(ExpectedConditions.elementToBeClickable(statusDropdown));

        driver.findElement(statusDropdown).click();


        //SELECT ACTIVE OPTION
        By activeOption = By.xpath(xpathExpression"//button[@data-value='active']");
        wait.until(ExpectedConditions.elementToBeClickable(activeOption));
        driver.findElement(activeOption).click();

        //DIRECT SCROLL-TO-ELEMENT COMMAND
        ((JavascriptExecutor) driver).executeScript(
            script:"arguments[0].scrollIntoView({behavior:'instant', block:'center'});",
            driver.findElement(activeOption));
        driver.findElement(activeOption).click();
        
        //CONTINUE BUTTON
        By continueButton = By.xpath(xpathExpression:"//button[normalize-space(.)='Continue']");
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        driver.findElement(continueButton).click();


        //CLIENTS PAGE
        driver.manage().timeouts()implicitlyWait(Duration.ofSeconds(3));
        By clientsMenu = By.xpath(xpathExpression:"//a[@aria-label='Clients']");
        wait.until(ExpectedConditions.elementToBeClickable(clientsMenu));
        driver.findElement(clientsMenu).click();

        //VERIFY CLIENT EXISTS
        driver.manage().timeouts()implicitlyWait(Duration.ofSeconds(3));
        By createdClient = By.xpath("//a[normalize-space()='" + firstName + " Automation']");
        ((JavascriptExecutor) driver).executeScript(
            script:"arguments[0].scrollIntoView({behavior:'intant', block:'center'});",
            driver.findElement(createdClient));
        wait.until(ExpectedConditions.visibilityOfElementLocated(createdClient));
        WebElement client = driver.findElement(createdClient);
        boolean clientFound = client.isDisplayed();
        Assert.assertTrue(clientFound, message:"Client was not found in Clients list");
        System.out.println("Client successfully verified: " + firstName);
    }


    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();

        }
    }
}
