package com.vlocity.qe;

import com.vlocity.page.HttpApiPage;
import com.vlocity.page.WikiHomePage;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static com.vlocity.util.PropertyReader.prop;

/**
 * This class verifies elements on the wikipedia homepage.
 */
public class WikipediaTest {

	private Logger log = LoggerFactory.getLogger(WikipediaTest.class);

	private WebDriver driver;
	private ElementFinder finder;
	private HttpApiPage apiPage;
	private WikiHomePage homePage;
	private String logg="";

	@BeforeClass
	public void setup() throws IOException {

        /*
            WebDriverManager.chromedriver().setup() should automatically use the right
             driver for your Chrome version.  If it does not, you can choose a version manually
            see https://sites.google.com/a/chromium.org/chromedriver/downloads
            and update it as needed.

            WebDriverManager.chromedriver().version("74.0.3729.6").setup();
        */

        prop = new Properties();
        prop.load(Files.newInputStream(Paths.get(System.getProperty("user.dir")+"/src/test/resources/data.properties")));

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		finder = new ElementFinder(driver);
		apiPage = new HttpApiPage(driver);
		homePage = new WikiHomePage(driver);

		// adjust timeout as needed
		driver.manage().timeouts().implicitlyWait(Long.parseLong(prop.getProperty("implicitTimeout")), TimeUnit.SECONDS);

		driver.get(prop.getProperty("wikiHomePage"));
		driver.manage().window().maximize();
	}

	@Test
	public void sloganPresent() {

		String sloganClass = "localized-slogan";
		WebElement slogan = finder.findElement(By.className(sloganClass));

		Assert.assertNotNull(slogan, String.format("Unable to find slogan div by class: %s", sloganClass));

		log.info("Slogan text is {}", slogan.getText());

		Assert.assertEquals(slogan.getText(), "The Free Encyclopedia");
	}

    /**
	 * 1st Test.
	 *
     * Validating Http Response For Each Language Using DataProvider.
     * @param index {@link Integer} Representing Index In The List Of WebElement For href.
     */
	@Test(dataProviderClass = DataGenerator.class, dataProvider = "dpApi")
	public void testHyperLinksStatus(int index){

		apiPage.validateResponseCode(index);
	}

	/**
	 * 2nd Test.
	 *
	 * Validating If Language Extracted From WikiHomePage Is Available In The Known List Of Languages Using Data Provider.
	 * @param lang {@link String}
	 */
	@Test(dataProviderClass = DataGenerator.class, dataProvider = "languages")
	public void verifyLanguagePresent(String lang){

		homePage.verifyLanguagePresent(lang);
	}


	@AfterClass
	public void closeBrowser() {
		if (driver != null) {
			driver.close();
		}
	}
}
