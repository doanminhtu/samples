package iosApp;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.appium.java_client.ios.IOSDriver;

public class Ios_app {
	public static IOSDriver<WebElement> driver = null;

	@Given("^User starts a session on ios device$")
	public void start_an_ios_app_session() throws MalformedURLException {
		String deviceUdid = System.getenv("KOBITON_DEVICE_UDID");
		String deviceName = System.getenv("KOBITON_DEVICE_NAME");
		
		String deviceOrientation = System.getenv("KOBITON_SESSION_DEVICE_ORIENTATION");
		String captureScreenshots = System.getenv("KOBITON_SESSION_CAPTURE_SCREENSHOTS");
		String deviceGroup = System.getenv("KOBITON_SESSION_DEVICE_GROUP");
		String app = System.getenv("KOBITON_SESSION_APPLICATION_URL");
		String platformVersion = System.getenv("KOBITON_SESSION_PLATFORM_VERSION");
		String groupId = System.getenv("KOBITON_SESSION_GROUP_ID");

		URL kobitonServerUrl = new URL("https://" + username + ":" + apiKey + "@api.kobiton.com/wd/hub");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("sessionName", "Automation test ios app session");
		capabilities.setCapability("sessionDescription", "Automation test ios app session"); 
		capabilities.setCapability("deviceOrientation", ((deviceUdid == null) ? "portrait" : deviceUdid));  
		capabilities.setCapability("captureScreenshots", Boolean.parseBoolean((captureScreenshots == null) ? "true" : captureScreenshots)); 
		capabilities.setCapability("app", ((app == null) ? "https://s3-ap-southeast-1.amazonaws.com/kobiton-devvn/apps-test/demo/iFixit.ipa" : app)); 
		capabilities.setCapability("deviceGroup", ((deviceGroup == null) ? "KOBITON" : deviceGroup));
		capabilities.setCapability("platformName", "iOS");
		
		if (!isEmpty(deviceUdid)) {
			capabilities.setCapability("deviceUdid", deviceUdid);
		}
		else {
			capabilities.setCapability("deviceName", ((deviceName == null) ? "iPhone 6" : deviceName));
			capabilities.setCapability("platformVersion", platformVersion);
		}
		
		if (!isEmpty(groupId)) {
			capabilities.setCapability("groupId", groupId);
		}

		driver = new IOSDriver<WebElement>(kobitonServerUrl, capabilities);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	@Given("^User goes to Home page$")
	public void go_to_IFixit_Home_Page() {
		WebElement homepage = driver.findElementByXPath("//XCUIElementTypeButton[@name='START A REPAIR']");
		homepage.click();
		sleep(2);
	}

	@And("^User clicks on Car and Truck category$")
	public void click_on_Car_Truck_category() {
		driver.findElementByXPath("//*[@name='Car and Truck']").click();
	}

	@And("^User clicks on Acura category$")
	public void click_on_Acura_cateogry() {
		driver.findElementByXPath("//*[@name='Acura']").click();
		sleep(2);
	}

	@And("^User waits for Navigation Bar$")
	public void wait_for_Navigation_bar() {
		(new WebDriverWait(driver, 60))
	      .until(ExpectedConditions.elementToBeClickable(By.xpath("//XCUIElementTypeNavigationBar")));
	}

	@Then("^Verify five items display: Acura Integra, Acura MDX, Acura RL, Acura TL, Acura TSX$")
	public void verify_five_items() {
		String acuraText = driver.findElementByXPath("//XCUIElementTypeNavigationBar").getAttribute("name");
	    boolean hasAcuraIntegra = driver.findElementByXPath("//XCUIElementTypeStaticText[@name='Acura Integra']")
	      .isDisplayed();
	    boolean hasAcuraMDX = driver.findElementByXPath("//XCUIElementTypeStaticText[@name='Acura MDX']").isDisplayed();
	    boolean hasAcuraRL = driver.findElementByXPath("//XCUIElementTypeStaticText[@name='Acura RL']").isDisplayed();
	    boolean hasAcuraTL = driver.findElementByXPath("//XCUIElementTypeStaticText[@name='Acura TL']").isDisplayed();
	    boolean hasAcuraTSX = driver.findElementByXPath("//XCUIElementTypeStaticText[@name='Acura TSX']").isDisplayed();
	    
	    Assert.assertEquals(acuraText, "Acura");
	    Assert.assertEquals(hasAcuraIntegra, true);
	    Assert.assertEquals(hasAcuraMDX, true);
	    Assert.assertEquals(hasAcuraRL, true);
	    Assert.assertEquals(hasAcuraTL, true);
	    Assert.assertEquals(hasAcuraTSX, true);
	}
	
	@Given("^User ends session on ios device$")
	public void end_an_ios_app_session() {
		try {
			if (driver != null)
				driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sleep(int seconds) {
	    try {
	      Thread.sleep(seconds * 1000);
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }
	  }
}