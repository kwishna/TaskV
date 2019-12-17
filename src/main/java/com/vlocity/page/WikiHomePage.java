package com.vlocity.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;

import static com.vlocity.util.PropertyReader.prop;

public class WikiHomePage {

	private Logger log = LoggerFactory.getLogger(WikiHomePage.class);

	WebDriver driver;

	public WikiHomePage(WebDriver driver){
		this.driver = driver;
	}

	public void verifyLanguagePresent(String lang){

		String langTextLocator = prop.getProperty("featuredLangText");
		List<WebElement> elements = driver.findElements(By.cssSelector(langTextLocator));

		List<String> allLanguages = elements.stream().map(e -> e.getText().trim()).collect(Collectors.toList());

		log.info("Asserting If "+lang+" Is Present In Wikiedia Featured Lanugauge List. "+allLanguages);
		Assert.assertTrue(allLanguages.contains(lang), lang+" Is Not Present In The Page");
	}
}
