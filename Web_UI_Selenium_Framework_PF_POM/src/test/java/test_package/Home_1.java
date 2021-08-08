package test_package;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import main_package.Base;
import pageObjects.Home_pageFactory;

public class Home_1 extends Base {

	public WebDriver driver;
	public static Logger log = LogManager.getLogger(Base.class.getName());

	@BeforeTest
	public void setup() throws IOException, InterruptedException {
		driver = initializeDriver();
		log.info("initializeDriver");

	}

	@Test(dataProvider = "getData")
	public void basePageNavigation(String user, String pass) throws IOException, InterruptedException {

		driver.get(prop.getProperty("url"));
		log.info("Opened URL");
		Thread.sleep(1000);

		// Using page object model
		// Home_pageObjectModel obj = new Home_pageObjectModel(driver);

		// Using page Factory
		Home_pageFactory obj = new Home_pageFactory(driver);

		obj.user().sendKeys(user);
		obj.pass().sendKeys(pass);
//		obj.logIn().click();

		// Assertion
		Assert.assertEquals(obj.insta_Title().getText(), "Instagram");
		log.info("Did assertion");
		
	}

	// Row stands for test count or data combinations
	// Column stands for how many values per each test

	@AfterTest
	public void teardown() {
		driver.quit();
	}

	@DataProvider
	public Object[][] getData() {

		Object[][] data = new Object[2][2];

		data[0][0] = "user_0";
		data[0][1] = "pass_0";

		data[1][0] = "user_1";
		data[1][1] = "pass_1";

		return data;

	}

}