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
        By loginButton = By.cssSelector("button[type='submit']");

        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));

        driver.findElement(emailField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();


        //WAIT FOR DASHBOARD
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));


        //OPEN "+" MENU
        By plusMenu = By.cssSelector("button[aria-label='Create']");
        wait.until(ExpectedConditions.elementToBeClickable(plusMenu));
        driver.findElement(plusMenu).click();


        //NEW CLIENT
        By newClientOption = By.xpath("//span[contains(text(),'New Client')]");
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
        By stateDropdown = By.cssSelector("button[id*='spds-input-dropdown']");
        wait.until(ExpectedConditions.elementToBeClickable(stateDropdown));

        driver.findElement(stateDropdown).click();


        //SELECT ACTIVE OPTION
        By activeOption = By.xpath("//*[text()='Activo']");
        wait.until(ExpectedConditions.elementToBeClickable(activeOption));
        driver.findElement(activeOption).click();

        //CONTINUE BUTTON
        By continueButton = By.xpath("//button[contains(text(),'Continuar')]");
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        driver.findElement(continueButton).click();


        //CLIENTS PAGE
        By clientsMenu = By.xpath("//a[contains(@href,'clients')]");
        wait.until(ExpectedConditions.elementToBeClickable(clientsMenu));
        driver.findElement(clientsMenu).click();

        //VERIFY CLIENT EXISTS
        By createdClient = By.xpath("//*[contains(text(),'" + firstName + "')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(createdClient));
        WebElement client = driver.findElement(createdClient);
        boolean clientFound = client.isDisplayed();
        Assert.assertTrue(clientFound, "Client was not found in Clients list");
        System.out.println("Client successfully verified: " + firstName);
    }


    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();

        }
    }
}
