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

class PageBase {
    protected WebDriver driver;
    protected WebDriverWait wait;

    private By logoutSelect = By.xpath("//span[@class='DFKWwVItcycZV1bKUOyay']");
    private By logoutButton = By.xpath("//a[@class='_1YWXCINvcuU7nk0ED-bta8']/div[text()='Log Out']");
    
    public PageBase(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }
    
    protected WebElement waitAndReturnElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    } 
    
    public String getBodyText() {
        WebElement bodyElement = this.waitAndReturnElement(By.tagName("body"));
        return bodyElement.getText();
    }

    public String getUrl() {
        return this.driver.getCurrentUrl(); 
    }

    public SubmitPage postClick() {
        By postButton = By.xpath("//span[@class='_2zZ-KGHbWWqrwGlHWXR90y']/button[@aria-label='Create Post']");
        this.waitAndReturnElement(postButton).click();

        return new SubmitPage(this.driver);
    }

    public MainPage logout() { 
        this.waitAndReturnElement(logoutSelect).click();
        this.waitAndReturnElement(logoutButton).click();

        this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='_3Wg53T10KuuPmyWOMWsY2F _2iuoyPiKHN3kfOoeIQalDT _2tU8R9NTqhvBrhoNAXWWcP HNozj_dKjQZ59ZsfEegz8 _2nelDm85zKKmuD94NequP0']")));

        return new MainPage(this.driver);
    }
   
}
