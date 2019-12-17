package com.vlocity.page;

import com.vlocity.qe.ElementFinder;

import org.apache.http.HttpResponse;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.testng.Assert;

import java.util.List;

import static com.vlocity.util.ApiHandler.handleGetRequest;
import static com.vlocity.util.PropertyReader.prop;

/**
 * Page Executing Api Related Test Implementations.
 */
public class HttpApiPage {

	private Logger log = LoggerFactory.getLogger(HttpApiPage.class);

	private WebDriver driver;
	private ElementFinder finder;

	public HttpApiPage(WebDriver driver){
		this.driver = driver;
		finder = new ElementFinder(this.driver);
	}

	public void validateResponseCode(int index){

		String locatorForLanguageDiv = prop.getProperty("featuredLang");
		String attribute = "href";

		List<WebElement> languageElements = finder.findElements(By.cssSelector(locatorForLanguageDiv));
		WebElement languageDiv;

		if (index <= languageElements.size()) {
			languageDiv = languageElements.get(index);
		}
		else{
			log.warn("Index Provided Exceeds The Number Of Languages Available! Possibly The Site DOM Has Got Changed");
			return;
		}

		String hrefLink = languageDiv.getAttribute(attribute);

		HttpResponse response = handleGetRequest(hrefLink);

		Assert.assertNotNull(response, "Response Is 'null' For URL - " + hrefLink);

		int actualStatusCode = response.getStatusLine().getStatusCode();
		int expectedStatusCode = 200;
		Assert.assertEquals(actualStatusCode, expectedStatusCode, "Http Response Mismatch For URL - " + hrefLink);
	}
}
