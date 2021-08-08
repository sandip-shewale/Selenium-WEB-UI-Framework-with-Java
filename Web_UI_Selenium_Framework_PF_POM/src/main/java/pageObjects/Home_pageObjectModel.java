package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Home_pageObjectModel {

	public WebDriver driver;

	public Home_pageObjectModel(WebDriver driver) {
		this.driver = driver;
	}

	private By user = By.cssSelector("input[name='username']");

	private By pass = By.xpath("//input[@name='password']");

	private By logIn = By.xpath("//div[contains(text(),'Log In')]");

	public WebElement user() {
		return driver.findElement(user);
	}

	public WebElement pass() {
		return driver.findElement(pass);
	}

	public WebElement logIn() {
		return driver.findElement(logIn);
	}

}
