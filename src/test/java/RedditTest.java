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
import java.util.*;  
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class RedditTest {
    public WebDriver driver;
    
    @BeforeTest 
    public void setup() {
        WebDriverManager.chromedriver().setup();
        
        // to block notifications
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("os", "Windows");
        capabilities.setCapability("os_version", "10");
        capabilities.setCapability("name", "Bstack-[Java] Sample file download");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        driver = new ChromeDriver(capabilities);
        driver.manage().window().maximize();
    }

    @Test(groups = { "login" })
    public void testLogin() {
        LoginPage loginPage = new LoginPage(this.driver);
               
        MainPage mainPage = loginPage.login("JaafarTest","123456789+-");
        String bodyText = mainPage.getBodyText();
        Assert.assertTrue(bodyText.contains("Reddit gets better when you join communities"));
        Assert.assertEquals(this.driver.getTitle(), "reddit: the front page of the internet");
    }
    
    @Test(dependsOnMethods={"testLogin"})
    public void testLogout() {
        // LoginPage loginPage = new LoginPage(this.driver);
               
        MainPage mainPage = new MainPage(this.driver); 
        mainPage = mainPage.logout();
        String bodyText = mainPage.getBodyText();
        Assert.assertTrue(bodyText.contains("Log In"));
    }

    @Test
    public void testStaticPage() {
        MainPage mainPage = new MainPage(this.driver);
        mainPage.staticPage();
        String bodyText = mainPage.getBodyText();
        Assert.assertTrue(bodyText.contains("Dive Into Anything"));
    }

    @Test(groups = { "login" })
    public void testSearch() {
        MainPage mainPage = new MainPage(this.driver); 

        String[] searchQueries={"dank memes","xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"};  
        for(String searchQuery : searchQueries) {  
            String bodyText;
            if(searchQuery == "dank memes")
            {
                SearchResultPage searchResultPage = mainPage.search(searchQuery,"//div[@class='_3llSmEBMCJTcO537oPxHIH']");
                bodyText = searchResultPage.getBodyText();
                Assert.assertTrue(bodyText.contains("Join"));
            } else {
                SearchResultPage searchResultPage = mainPage.search(searchQuery,"//div[@class='_1VDJFxZ-XJSB8yo1UyJzsi']");
                bodyText = searchResultPage.getBodyText();
                Assert.assertTrue(bodyText.contains("Sorry, there were no community results"));
            }
        }  
    }

    @Test
    public void testMultiplePage() {
        String[] pages ={"about","terms","blog"}; 
        String bodyText, URL;
        GeneralPage generalPage;
        for(String page : pages) {  
            MainPage mainPage = new MainPage(this.driver);
            if(page == "about") {
                generalPage = mainPage.multiplePage("//div[@class='_3f2nSSsPBqVDV6Sz82qgrR']/a[@href='https://about.reddit.com']");
                bodyText = generalPage.getBodyText();
                URL = generalPage.getUrl();
                Assert.assertEquals(URL, "https://www.redditinc.com/");
                Assert.assertTrue(bodyText.contains("Dive Into Anything"));
            } else if (page == "terms") {
                generalPage = mainPage.multiplePage("//div[@class='_3f2nSSsPBqVDV6Sz82qgrR']/a[@href='https://www.redditinc.com/policies/user-agreement']");
                bodyText = generalPage.getBodyText();
                URL = generalPage.getUrl();
                Assert.assertEquals(URL, "https://www.redditinc.com/policies/user-agreement");
                Assert.assertTrue(bodyText.contains("Reddit User Agreement"));
            } else {
                generalPage = mainPage.multiplePage("//div[@class='_3f2nSSsPBqVDV6Sz82qgrR']/a[@href='http://www.redditblog.com/']");
                bodyText = generalPage.getBodyText();
                URL = generalPage.getUrl();
                Assert.assertEquals(URL, "https://redditblog.com/");
                Assert.assertTrue(bodyText.contains("Reddit Sets Up Shop in Canada"));
            }
        } 
    }

    @Test(groups = { "login" })
    public void testPost() {
        MainPage mainPage = new MainPage(this.driver); 

        SubmitPage submitPage = mainPage.postClick();
        GeneralPage generalPage = submitPage.submitPost();
        String bodyText = generalPage.getBodyText();
        Assert.assertTrue(bodyText.contains("Comment as JaafarTest"));
    }

    @Test(groups = { "login" })
    public void uploadTest() {
        MainPage mainPage = new MainPage(this.driver); 

        SubmitPage submitPage = mainPage.postClick();
        GeneralPage generalPage = submitPage.submitPostWithUpload();
        String bodyText = generalPage.getBodyText();
        Assert.assertTrue(bodyText.contains("Comment as JaafarTest"));
    }

    @Test		
    public void hoverTest()					
    {		
        MainPage mainPage = new MainPage(this.driver);
        mainPage.hover();
	}

    @Test		
    public void dragDropTest()					
    {		
        GeneralPage generalPage = new GeneralPage(this.driver);
        generalPage.dragDrop();
	}

    @Test
    public void historyTest() {
        MainPage mainPage = new MainPage(this.driver);
        mainPage.back();
        Assert.assertEquals(mainPage.getUrl(), "https://www.reddit.com/");
    }

    // Cookies Test
    @Test
	public void addCookie()
	{
        MainPage mainPage = new MainPage(this.driver);

		Cookie name = new Cookie("mycookie", "123456789123");
		this.driver.manage().addCookie(name);
		
		Set<Cookie> cookiesList =  this.driver.manage().getCookies();
		for(Cookie getcookies :cookiesList) {
		    System.out.println(getcookies );
		}
	}

    @Test
	public void deleteCookie()
	{
		MainPage mainPage = new MainPage(this.driver);

        Cookie name = new Cookie("mycookie", "123456789123");
		this.driver.manage().addCookie(name);
        this.driver.manage().deleteCookieNamed("mycookie");
	}
    // End of Cookies Test

    @AfterTest 
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
