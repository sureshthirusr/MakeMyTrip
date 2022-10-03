package com.makemytrip.pom;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.makemytrip.baseclass.BaseClass;

public class SelectFlightPage extends BaseClass {
	public static WebDriver driver;

	@FindBy(xpath = "//*[@id='sorting-togglers']/div[3]/div/span/span[1]")
	private WebElement sortByPrice;

	@FindBy(xpath = "//*[@id='left-side--wrapper']/div/div/div[1]/div[1]/div/div[2]/ul/li[1]/a/p")
	private WebElement lowToHigh;

	@FindBy(xpath = "(//div[@class='paneView'])[1]//*[contains(@id,'flightCard')]//descendant::div[@class='makeFlex column relative splitfare ']/p")
	private List<WebElement> flightPrices;

	@FindBy(xpath = "(//div[@class='paneView'])[1]//*[contains(@id,'flightCard')]/div/div[2]/div[1]/div[2]/p")
	private List<WebElement> flightDurations;

	@FindBy(xpath = "(//*[contains(@id,'flightCard')]/div/div[2]/div[2]/span/span[@class='outer'])[1]")
	private WebElement selectFlight;

	@FindBy(xpath = "(//*[contains(@id,'flightCard')]/div/div[2]/div[2]/div/p)[1]")
	private WebElement fare_Details;

	@FindBy(xpath = "//button[contains(text(),'Book Now')]")
	private WebElement bookNow_Button;

	@FindBy(xpath = "//*[@id='ow-domrt-jrny']/div/div[2]/label/div[1]/span[2]/span")
	private WebElement flightName;

	@FindBy(xpath = "//div[@class='multifareOuter']//descendant ::div/button[text()='Continue']")
	private WebElement continueBtn;

	@FindBy (xpath="//div[@class='multifareOuter']//descendant ::div/button[text()='Continue']")
	private WebElement Continue_Btn;

	@FindBy(xpath = "//*[@id='rev-header']/div/div/div/h4[text()='Review your booking']")
	private WebElement reviewAndConfirm;

	@FindBy(xpath = "//*[@id='JOURNEY_SECTION']/div/div/div[1]/section/div[2]/div[1]/div[1]/p/span")
	private List<WebElement> reviewdFlightName;

	public SelectFlightPage(WebDriver ldriver) {
		this.driver = ldriver;
		PageFactory.initElements(driver, this);
	}

	public WebElement getSortByPrice() {
		return sortByPrice;
	}

	public WebElement getLowToHigh() {
		return lowToHigh;
	}

	public List<WebElement> getFlightPrices() {
		return flightPrices;
	}

	public List<WebElement> getflightDurations() {
		return flightDurations;
	}

	public WebElement getselectFlight() {
		return selectFlight;
	}

	public WebElement getbookNow_Button() {
		return bookNow_Button;
	}

	public WebElement getflightName() {
		return flightName;
	}

	public WebElement getcontinueBtn() {
		return continueBtn;
	}

	public WebElement getreviewAndConfirm() {
		return reviewAndConfirm;
	}

	public List<WebElement> getreviewdFlightName() {
		return reviewdFlightName;
	}

	public WebElement getfare_Details() {
		return fare_Details;
	}

}
