import org.junit.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import java.util.concurrent.TimeUnit;

class LoginPage extends PageBase {
    
    private By usernameInput = By.name("username");
    private By passwordInput = By.name("password");
    private By loginSubmitButton = By.xpath("//button[@class='AnimatedForm__submitButton m-full-width']");

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://www.reddit.com/login/");

    }    

    public MainPage login(String username, String password) {
        this.waitAndReturnElement(usernameInput).sendKeys(username + "\n");
        this.waitAndReturnElement(passwordInput).sendKeys(password + "\n");
        this.waitAndReturnElement(loginSubmitButton).click();
 
        // wait to login
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='DFKWwVItcycZV1bKUOyay']")));

        return new MainPage(this.driver);
    }

}
