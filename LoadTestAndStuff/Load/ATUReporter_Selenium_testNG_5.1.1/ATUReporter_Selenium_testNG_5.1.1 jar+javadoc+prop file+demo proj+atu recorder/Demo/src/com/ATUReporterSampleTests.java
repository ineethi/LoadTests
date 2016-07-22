package com;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class,
		MethodListener.class })
public class ATUReporterSampleTests {
	{
		System.setProperty("atu.reporter.config", "D:\\img\\atu.properties");
	}

	WebDriver driver;

	@BeforeClass
	public void startBrowser() {
		driver = new FirefoxDriver();
		// driver = new InternetExplorerDriver();
		ATUReports.setWebDriver(driver);
		driver.get("http://demo.opensourcecms.com/wordpress/wp-login.php");
		ATUReports.indexPageDescription = "My Project Description";
	}

	@Test
	public void deprecatedLogsTest() {
		Reporter.log("<a href=\"#\">My Logs using TestNG Reporter</a>");
		ATUReports.add("<a href=\"#\">My Logs using ATU Reporter</a>", true);
		ATUReports.add("Befrore dodo", true);
	}

	@Test
	public void testNewLogs() throws AWTException, IOException {

		ATUReports.setAuthorInfo("Author ATU", "26-May-2014 3:46", "1.2");

		ATUReports.add("INfo Step", LogAs.INFO, new CaptureScreen(
				ScreenshotOf.BROWSER_PAGE));
		ATUReports.add("Pass Step", LogAs.PASSED, new CaptureScreen(
				ScreenshotOf.DESKTOP));
		WebElement element = driver
				.findElement(By.xpath("/html/body/div/h1/a"));
		ATUReports.add("Warning Step", LogAs.WARNING,
				new CaptureScreen(element));
		ATUReports.add("Fail step", LogAs.FAILED, new CaptureScreen(
				ScreenshotOf.DESKTOP));
	}

	@Test
	public void testNewLogsForContinuedStepsAfterFailed() throws AWTException,
			IOException {
		ATUReports.add("INfo Step", LogAs.INFO, new CaptureScreen(
				ScreenshotOf.BROWSER_PAGE));
		ATUReports.add("Pass Step", LogAs.PASSED, new CaptureScreen(
				ScreenshotOf.DESKTOP));
		WebElement element = driver
				.findElement(By.xpath("/html/body/div/h1/a"));
		ATUReports.add("Warning Step", LogAs.WARNING,
				new CaptureScreen(element));
		ATUReports.add("Fail step", LogAs.FAILED, new CaptureScreen(
				ScreenshotOf.DESKTOP));
		System.out.println("TEST LOG RUNS");
		ATUReports.add("INfo Step", LogAs.INFO, new CaptureScreen(
				ScreenshotOf.BROWSER_PAGE));
		ATUReports.add("Pass Step", LogAs.PASSED, new CaptureScreen(
				ScreenshotOf.DESKTOP));
		ATUReports.add("Warning Step", LogAs.WARNING,
				new CaptureScreen(element));
		ATUReports.add("Fail step", LogAs.FAILED, new CaptureScreen(
				ScreenshotOf.DESKTOP));
	}

	@Test(dataProvider = "data")
	public void dodo(String a, String b) throws InterruptedException {
		ATUReports
				.setTestCaseReqCoverage("This test is mapped to Login Requirement TC_001");
		ATUReports.setAuthorInfo("SampleAuthor1", "12-Nov-2013 3:46", "1.2");
		ATUReports.add("Enter UName", "admin", "login", "login", true);
		ATUReports.add("Enter Passs", "demo123", "login", "login", true);
		ATUReports.add("Click on login Button", false);
		if (a.equals("3")) {
			Assert.fail("Test Second Iteration Fail");
		}
	}

	@Test
	public void VerifyLogin_failure() throws InterruptedException {
		ATUReports.setAuthorInfo("SampleAuthor3", "12-Nov-2013 3:46", "1.2");
		ATUReports.add("Enter Password", "PASS", true);
		// Thread.sleep(2000);
		Assert.fail("I have thrown Exception");
	}

	@Test(dependsOnMethods = "VerifyLogin_failure")
	public void VerifySkipping() throws InterruptedException {
		ATUReports.setAuthorInfo("SampleAuthor3", "12-Nov-2013 3:46", "1.2");
		ATUReports.add("Enter Password", "PASS", true);
		Assert.fail("hello");
	}

	@DataProvider(name = "data")
	public Object[][] data() {
		return new Object[][] { { "1", "2" }, { "3", "4" } };
	}

	@AfterClass
	public void close() {
		driver.quit();
	}
}
