package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Home_pageFactory {

	public WebDriver driver;

	public Home_pageFactory(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//input[@name='username']")
	private  WebElement user;

	@FindBy(xpath = "//input[@name='password']")
	private  WebElement pass;

	@FindBy(xpath = "//div[contains(text(),'Log In')]")
	private  WebElement logIn;
	
	@FindBy(xpath = "//h1[normalize-space()='Instagram']")
	private  WebElement insta_Title;

	public WebElement user() {
		return user;
	}

	public WebElement pass() {
		return pass;
	}

	public WebElement logIn() {
		return logIn;
	}
	
	public WebElement insta_Title() {
		return insta_Title;
	}

}
