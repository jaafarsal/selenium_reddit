import org.junit.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import com.github.javafaker.Faker;

class SubmitPage extends PageBase {

    private By selectBy = By.xpath("//input[@class='_1MHSX9NVr4C2QxH2dMcg4M']");
    private By titleBy = By.xpath("//div[@class='_2wyvfFW3oNcCs5GVkmcJ8z']/textarea[@placeholder='Title']");
    private By descriptionBy = By.xpath("//div[@role='textbox']");
    private By postButtonBy = By.xpath("//div[@class='_1T0P_YQg7fOYLCRoKl_xxO ']/button[text()='Post']");
    
    private By imageButtonBy = By.xpath("//button[text()='Images & Video']");
    private By inputFileBy = By.xpath("//input[@class='sU2P34us34ODfjtvAFHEh']");

    public SubmitPage(WebDriver driver) {
        super(driver);
        // this.driver.get("https://www.reddit.com/submit");
    }    

    public GeneralPage submitPost() {
        Faker faker = new Faker();
        String title = faker.esports().game(); 
        String name = faker.name().fullName(); 
        String streetAddress = faker.address().streetAddress();

        // fill drop-down
        this.waitAndReturnElement(selectBy).sendKeys("u/JaafarTest");
        
        this.waitAndReturnElement(titleBy).sendKeys(title);
        this.waitAndReturnElement(descriptionBy).sendKeys(name + streetAddress);

        this.wait.until(ExpectedConditions.elementToBeClickable(postButtonBy));	

        this.waitAndReturnElement(postButtonBy).click();

        this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='"+title+"']")));

        return new GeneralPage(this.driver);
    }
       
    public GeneralPage submitPostWithUpload() {
        Faker faker = new Faker();
        String title = faker.gameOfThrones().house(); 
        File file = new File("src/test/java/resources/reddit.jpg");

        this.waitAndReturnElement(imageButtonBy).click();

        // fill drop-down
        this.waitAndReturnElement(selectBy).sendKeys("u/JaafarTest");
        this.waitAndReturnElement(titleBy).sendKeys(title);

        // display the input file cuz it's hidden in the website
        JavascriptExecutor js = (JavascriptExecutor) this.driver;
        js.executeScript("return document.getElementsByClassName('sU2P34us34ODfjtvAFHEh')[0].style.display = 'block';");
        
        this.waitAndReturnElement(inputFileBy).sendKeys(file.getAbsolutePath());

        this.wait.until(ExpectedConditions.elementToBeClickable(postButtonBy));	
        this.waitAndReturnElement(postButtonBy).click();

        this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='"+title+"']")));

        return new GeneralPage(this.driver);
    }
}
