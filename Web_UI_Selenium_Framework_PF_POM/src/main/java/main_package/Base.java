package main_package;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {

	public WebDriver driver;
	public Properties prop;

	@Test
	public WebDriver initializeDriver() throws IOException {

		// Fetch global variables from .properties file
		String path = System.getProperty("user.dir");
		FileInputStream fis = new FileInputStream(path + "\\src\\main\\java\\main_package\\data.properties");
		prop = new Properties();
		prop.load(fis);

		// Get browser value from data.properties file
		String browser_name = prop.getProperty("browser");

		// Get browser value from Maven
		// mvn test -Dbrowser=chrome
		// String browser_name = System.getProperty("browser");

		if (browser_name.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browser_name.equals("chromeheadless")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			driver = new ChromeDriver(options);
		} else if (browser_name.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browser_name.equals("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}

		driver.manage().window().maximize();
//		driver.get(url);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		return driver;

	}

	public String getScreenShotPath(String getMethodName, WebDriver driver) throws IOException {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir") + "//reports//" + getMethodName + ".png";
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;

	}

}
