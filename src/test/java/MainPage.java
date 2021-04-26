import org.junit.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import java.util.concurrent.TimeUnit;

class MainPage extends PageBase {

    private By searchBarBy = By.name("q");

    private By aboutBy = By.xpath("//div[@class='_3f2nSSsPBqVDV6Sz82qgrR']/a[@href='https://about.reddit.com']");

    public MainPage(WebDriver driver) {
        super(driver);
        this.driver.get("https://www.reddit.com/");
    }    

    public SearchResultPage search(String searchQuery, String selector) {
        this.waitAndReturnElement(searchBarBy).sendKeys(searchQuery + "\n");

        this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selector)));

        return new SearchResultPage(this.driver);
    }

    public AboutPage staticPage() {
        // this.driver.get("https://www.reddit.com/");
        this.waitAndReturnElement(aboutBy).click();
        return new AboutPage(this.driver);
    }

    public GeneralPage multiplePage(String selector) {
        // this.driver.get("https://www.reddit.com/");
        this.waitAndReturnElement(By.xpath(selector)).click();
        return new GeneralPage(this.driver);
    }

    public void hover() {
        WebElement ele = this.waitAndReturnElement(aboutBy);
        Actions action = new Actions(this.driver);

        action.moveToElement(ele).perform();
    }

    public void back() {
        this.waitAndReturnElement(aboutBy).click();
        this.driver.navigate().back();
    }

}
