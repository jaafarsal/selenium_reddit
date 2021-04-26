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


class GeneralPage extends PageBase {

    public GeneralPage(WebDriver driver) {
        super(driver);
    }    

    public void dragDrop() {
        // used external website for dragNdrop test ,since there is no drag and drop items on reddit
        this.driver.get("http://demo.guru99.com/test/drag_drop.html");	
        WebElement From = this.waitAndReturnElement(By.xpath("//*[@id='credit2']/a"));	
        WebElement To = this.waitAndReturnElement(By.xpath("//*[@id='bank']/li"));	
        Actions act = new Actions(this.driver);	

        act.dragAndDrop(From, To).build().perform();
    }

}
