package frontEndTest;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * @author Fathea Chowdhury
 *
 */
public class frontEnd {

	public static void main(String[] args) {
		String searchString = "test";
		String specifyLanguage = "Italiano";
		 WebDriver driver =  new HtmlUnitDriver();
		 
		 /**
		  * Navigate to the Wikipedia home page, http://www.wikipedia.org/.
		  */
		 
		 driver.get("http://www.wikipedia.org/");
		 
		 /**
		 (a) Type in a string given as parameter in the search input eld.
		 (b) Select English as the search language.
		 (c) Click the search button.
		 **/
		 WebElement searchInput = driver.findElement(By.id("searchInput"));
		 searchInput.sendKeys(searchString);
		 
		 Select languageDropdownList = new Select(driver.findElement(By.id("searchLanguage")));   
		 languageDropdownList.selectByVisibleText("English");
		 
		 WebElement searchButton = driver.findElement(By.name("go"));
		 searchButton.click();
		 
		 /**
		  * Validate that the first heading of the search results page matches the search string (ignoring the case)
		 */
		
		 WebElement firstHeading = driver.findElement(By.xpath("//*[@id=\"firstHeading\"]/span")); 
		 Assert.assertEquals(firstHeading.getText().toLowerCase(),searchString.toLowerCase());
		/** 
		 Verify that the search results page is available in a language given as
		 parameter.
		 **/
		 WebElement newLanguage = driver.findElement(By.xpath("//*[@id=\"p-lang\"]/div/ul/li[12]/a"));
		 Assert.assertEquals(newLanguage.getText(), specifyLanguage);
		
		/**
		 * Navigate to the search results page in that language.
		 */
		newLanguage.click();
		
		/**
		Validate that the search results page in the new language includes a
		link to the version in English.
		**/
		 Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"p-lang\"]/div/ul/li[7]/a")).isDisplayed());	
	}



}
