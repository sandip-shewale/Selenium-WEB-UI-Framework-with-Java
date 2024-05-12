# Selenium WEB UI Framework with Java

============================================================

Building Framework from Scratch -

Framework is about how better we maintain / organize test cases script

src/test/java - Only test cases should go

src/main/java - Common re-usable utilities e.g. driver initialization, browser selection, excel operations, log4j, extent reporting

============================================================

197. Create Maven Test project & Configure Framework dependencies

= Creation of Maven project

- Create Maven project using Artifact_ID and Group_ID - Artifact_ID is nothing but project name.
- Add dependencies into pom.xml file e.g. - Selenium-java, TestNG, webdrivermanager etc
- Using webdrivermanager maven dependency will download all browser drivers


============================================================

198. Design base Test with global variables setup

= Creating base and utility functions

- Create base class inside src/main/java to execute every time
- To maintain global variable create .properties file	
- Using # add comments in .properties file
- Set implicitlyWait at base class so it will be applicable for all test cases
- return webdriver object from Base class method so after calling base class method everyone can access driver object

= Acess webdriver object into different class using Inheritance concept 

- Inheritance - Class_B extends Class_A 

============================================================

199. Implement Pageobject Mechanism to drive tests

= Organizing page object model

- Create package with pageobjects inside src/main/java

*** VIP Question - How to access page object class into test cases class and how to avoid nullpointerexception
*** VIP Answer - If we want to access page object class into page test case then need to create an object of page object class
and we need to pass driver object while creating an object of page object class and need to create constructor in page object class 
inside constructor we will use this keyword to call driver object inside page object class.

*** VIP Question - Why / How we use constructor in framework.
*** VIP Answer - If we want to access page object class into page test case then need to create an object of page object class
and we need to pass driver object while creating an object of page object class and need to create constructor in page object class 
inside constructor we will use this keyword to call driver object inside page object class.

POM Implementation divided into two categories -

1. Page Object Model	
- Finding web elements using By	
- POM does not provide lazy initialization	
- Page Object Model is a design pattern	
- In POM, one needs to initialize every page object individually	

// In page object model file

By username = By.xpath("//input[@name='username']");

public WebElement username() {
	return driver.findElement(username);
}

// In test case file

obj.username().sendKeys("username");

2. Page Factory
- Finding web elements using @FindBy
- Page Factory does provide lazy initialization
- PageFactory is a class that provides the implementation of the Page Object Model design pattern
- In PageFactory, all page objects are initialized by using the PageFactory.initElements(driver, this); method

// In page factory file

// Create constructor in page factory file

public aa_login_page_objects_pageFactory(WebDriver driver) {
	this.driver = driver;
	PageFactory.initElements(driver, this);
}

@FindBy(xpath = "//input[@name='username']")
	WebElement username;

public WebElement username() {
	return username;
}

// In test case file

obj.username().sendKeys("username");


============================================================

200. Create Multiple tests with centralized data

= Adding Tests using Page Object Model or Page Factory

============================================================

201. Parameterizing the Tests with multiple data with TestNG

= Data Driven / Parameterizing Tests

- If we want to send multiple data into same test case then we can do with the help of DataProvider annotation in TestNG.	

// create @DataProvider 

@DataProvider
public Object[][] getdata() {
	
	Object[][] data = new Object[1][1];
	data[0][0] = "user_0";
	data[0][1] = "pass_0";
	
	return data;
	
}

// Use DataProvider

@Test(dataProvider = "getData")

public void basePageNavigation(String user, String pass)

// Exception [Utils] [ERROR] [Error] java.lang.ArrayIndexOutOfBoundsException: 1

If we are sending two values using @DataProvider Object[1][1]; then value should be two in object but in declaration starts with [0]

@DataProvider
public Object[][] getdata() {	
	Object[][] data = new Object[1][1];
	data[0][0] = "user_0";
	data[0][1] = "pass_0";	
	return data;	
}

============================================================

202. Asserting the Test cases with validations

Assert.assertEquals(obj.insta_Title().getText(), "Insta");
java.lang.AssertionError: expected [Insta] but found [Instagram]

============================================================

204. Adding Tests to Suite in framework guidelines

= Converting into TestNG Framework

- Right click on project -> TestNG -> Convert to TestNG it will auto create testng.xml file

============================================================

206. Configuring Tests with setup and Teardown in TestNG framework

- Add @AfterTest and @BeforTest annotations for opening and closing browser

= Link testng.xml file in pom.xml using maven-sirefire-plugin

Using => maven test => command pom.xml file will get execute with testng.xml file

<build>
	<plugins>
		<plugin>
			<groupId>org.apache.maven.plugins</groupId>
			<artifactId>maven-surefire-plugin</artifactId>
			<version>2.21.0</version>
			<configuration>
				<suiteXmlFiles>
					<suiteXmlFile>testng.xml</suiteXmlFile>
				</suiteXmlFiles>
			</configuration>
		</plugin>
	</plugins>
</build>

============================================================

= Log4j API for logging inside Framework

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public static Logger log = LogManager.getLogger(Base.class.getName());

We need to link log4j API with Maven find below steps -

- maven resources filtering
- Means we put some files inside - src/main/java/resources
- logs folder will automatically get create inside project 

<resources>
  <resource>
    <directory>src/main/java/resources</directory>
    <filtering>true</filtering>
  </resource>
</resources>

============================================================

208. Fixing the framework issues by tweaking TestNG xml file

Question - How to run tests in parallel mode.
Answer - We can run tests in parallel mode using initialize driver separately in every individual class 

Question - Client requirement is after executing every test case close the browser but if we linked all test cases inside single
test then sequentially all test cases will get execute and at the end driver will get close 
(run tests in sequentially open and close browser for every test)
Answer - Write test individual test case inside multiple tests in testng.xml file 

Question - How to run tests parallely
Answer - We can run tests parallely using parallel keyword at suite level and we need to mention what we need to run parallely

NOTE - All tests will start execution at same time

<suite name="Suite" parallel = "tests">

// Before

<test name="Test">
    <classes>
      <class name="test_package.Home_1"/>
      <class name="test_package.Home_2"/>
    </classes>
</test>

// After

<test name="Home_1">
	<classes>
		<class name="test_package.Home_1" />
	</classes>
</test> <!-- Test -->
<test name="Home_2">
	<classes>
		<class name="test_package.Home_2" />
	</classes>
</test> <!-- Test -->

============================================================

= TestNG listeners

- With the help of listeners we can perform multiple actions on testPass and on testFail
- e.g. We can capture screenshot on test failure
- We access listeners with the help of TestNG ITestListener interface

- If you will not get any import unimplemented methods message then => Right click(on the Listeners class ) 
-> go to source-> click on overide/implement methods -> select the check boxes for the ITest listener 
(make sure all check box inside it should be checked )->click on oK. That's it !!!

public class Listeners implements ITestListener 

============================================================

= Screenshot on Test Failures

- On latest java if we want to use FileUtils then we need to import new package in pom.xml => Apache Commons IO

FileUtils.copyFile(ts, new(File(destinationFile)));

Question - How to get failed test name
Answer - We to get failed test name using ITestListener => onTestFailure

String getMethodName = result.getMethod().getMethodName();
// We can pass variable to getScreenShotPath which is initialized in base class
getScreenShotPath(getMethodName);

Question - How to get methods from class into Listeners class
Answer - Using extends keyword inheritance concept

public class Listeners extends Base implements ITestListener

210. How to send Driver object of TestInstance to Listener on Test failure

If we want to testcases parallely then we need to pass driver instance object from Listeners class to getScreenShotPath method
In parallel execution multiple driver instances get create so using this we can capture screenshot from correct browser

String getMethodName = result.getMethod().getMethodName();
	WebDriver driver = null;

	try {
		driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver")
				.get(result.getInstance());
	} catch (Exception e) {

	}
	try {
		getScreenShotPath(getMethodName, driver);
	} catch (IOException e) {
		e.printStackTrace();
	}
	
NOTE - Whenever we create listeners in this case we need to inform our testng.xml file under suite


<listeners>
<listener class-name ="test_package.Listeners"/>
</listeners>

211. Extent reports on Generation on Test Execution

ThreadLocal class will help in parallel execution.

The ThreadLocal class is used to create thread local variables which can only be read and written by the same thread.

ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

Question - In parallel execution how to handle extent report test
Answer - We can handle parallel execution with the help of ThreadSafe - ThreadLocal

============================================================

