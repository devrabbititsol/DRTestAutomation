package utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
public class BaseClass {
	public WebDriver driver;
	/*
	 * public static void launchApplication() { driver = new FirefoxDriver();
	 * driver.get("xxxxxx"); waitForPageToLoad();
	 * driver.manage().window().maximize();
	 * 
	 * }
	 */
	// Explicit wait method
	public static WebElement waitForExpectedElement(WebDriver driver, final By locator, int time) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	public static WebElement waitForExpectedElement(WebDriver driver, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 120);
		return wait.until(ExpectedConditions.visibilityOf(element));
	}
	// Explicit wait
	public static WebElement waitForExpectedElement(WebDriver driver, final By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 120);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	// Explicit wait method
	public boolean objectExists(WebDriver driver, final By locator) {
		try {
			waitForPageToLoad();
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	// Method for Retry and It excutes the failed test case based on our count
	public class Retry implements IRetryAnalyzer {
		private int count = 0;
		private static final int maxTry = 3;
		public boolean retry(ITestResult iTestResult) {
			if (!iTestResult.isSuccess()) { // Check if test not succeed
				if (count < maxTry) { // Check if maxtry count is reached
					count++; // Increase the maxTry count by 1
					iTestResult.setStatus(ITestResult.FAILURE); // Mark test as failed
					return true; // Tells TestNG to re-run the test
				} else {
					iTestResult.setStatus(ITestResult.FAILURE); // If maxCount reached,test marked as failed
				}
			} else {
				iTestResult.setStatus(ITestResult.SUCCESS); // If test passes, TestNG marks it as passed
			}
			return false;
		}
	}
	// Explicit wait method (While Script Execution we need to pass time limit)
	public  boolean objectExists(WebDriver driver, final By locator, int timeout) {
		try {
			waitForPageToLoad();
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	// Explicit wait method (While Script Execution we need to pass time limit
	public void waitForPageToLoad() {
		(new WebDriverWait(driver, 60)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return (((org.openqa.selenium.JavascriptExecutor) driver).executeScript("return document.readyState")
						.equals("complete"));
			}
		});
	}
}
