package com.makemytrip.stepdefinition;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.aventstack.extentreports.Status;
import com.makemytrip.baseclass.BaseClass;
import com.makemytrip.pom.HomePage;
import com.makemytrip.runner.RunWithChrome;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Search_Flight_StepDefinition extends BaseClass {
	public static WebDriver driver = RunWithChrome.driver;
	public static Properties prop = RunWithChrome.prop;
	public static HomePage hp = new HomePage(driver);

	// public static PageObjectManager pom = new PageObjectManager(driver);

	@Given("^User launch the application$")
	public void user_launch_the_application() throws Throwable {
		test = extent.createTest("Search Flight");
		test.assignCategory("Sanity Test");
		getUrl();
		test.log(Status.INFO, "Application Launched");
	}

	@When("^User selects the flight option to travel$")
	public void user_selects_the_flight_option_to_travel() throws Throwable {
		//driver.switchTo().frame("webklipper-publisher-widget-container-notification-frame");
		Thread.sleep(2000);
	//	driver.findElement(By.xpath("//*[@id=\"webklipper-publisher-widget-container-notification-close-div\"]/i")).click();
		hp.selectFlightsMenu();
		test.log(Status.INFO, "Clicked on Flight menu");
	}

	@When("^User checks on round trip as option$")
	public void user_checks_on_round_trip_as_option() throws Throwable {
		hp.selectRoundtripMenu();
		test.log(Status.INFO, "Selected round trip");
	}

	@When("^User enter \"([^\"]*)\" as source city$")
	public void user_enter_as_source_city(String Source_city) throws Throwable { 
		hp.enterDepartureCity(Source_city);
		test.log(Status.INFO, "Entered the departure city");
	}

	@When("^User enter \"([^\"]*)\" as destination city$")
	public void user_enter_as_destination_city(String Destination_city) throws Throwable { 
		hp.enterReturnCity(Destination_city);
		test.log(Status.INFO, "Entered return city");
	}

	@When("^User enter the departure date$")
	public void user_enter_the_departure_date() throws Throwable {
		hp.enterDepartureDate();
		test.log(Status.INFO, "Entered Departure date");
	}

	@When("^User enter the return date$")
	public void user_enter_the_Return_date() throws Throwable {
		Thread.sleep(2000);
		hp.enterReturnDate();
		test.log(Status.INFO, "Entered return date");
	}

	@When("^User click on the search button$")
	public void user_click_on_the_search_button() throws Throwable {
		clickonWebelement(hp.getSearchBtn());
		test.log(Status.INFO, "clicked the search button");
	}

	@Then("^User navigates to Flight details page$")
	public void user_navigates_to_Flight_details_page() throws Throwable {
		System.out.println("User navigates into flight details " + CurrentURL());
	}

}
