package com.makemytrip.stepdefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.aventstack.extentreports.Status;
import com.google.common.collect.Lists;
import com.makemytrip.baseclass.BaseClass;
import com.makemytrip.pom.SelectFlightPage;
import com.makemytrip.runner.RunWithChrome;
import com.makemytrip.util.UtilFunctions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Lowest_Fastest_Selection_StepDefinition extends BaseClass {

	public static WebDriver driver = RunWithChrome.driver;
	public static SelectFlightPage flightInfo = new SelectFlightPage(driver);

	public static List<WebElement> flightPrices = new ArrayList<WebElement>();
	public static List<Integer> pricesList = new ArrayList<Integer>();
	public static List<WebElement> flightDurations = new ArrayList<WebElement>();
	public static List<String> DuraionList = new ArrayList<String>();
	public static Map<Integer, Integer> Min_PriceAndDuration = new HashMap<>();
	public static int Lowest_Price;
	public static String Minimum_Duration;
	public static String Flight_Name;

	@Given("^user view number of flights with unordered$")
	public void user_view_number_of_flights_with_unordered() throws Throwable {
		test = extent.createTest("Check the Lowest Price & Fastest flight");
		test.assignCategory("Regression Test");
	}

	@When("^User filter the low to high option from the drop down$")
	public void user_filter_the_low_to_high_option_from_the_drop_down() throws Throwable {
		try {
			try {
				// new pop up
			//	driver.findElement(By.xpath("//button[contains(text(),'OKAY')]")).click();
			} catch (Exception e) {
				throw new Exception("Pop up is not present");
			}
			// flightInfo.getSortByPrice().click();
			test.log(Status.INFO, "Clicked on Sort By Price");
			Thread.sleep(2000);
			// flightInfo.getLowToHigh().click();
			test.log(Status.INFO, "Clicked on Low to High Option");
		} catch (Exception e) {
			throw new Exception();
		}
	}

	@When("^User counts the number of flights in the filtered list$")
	public void user_counts_the_number_of_flights_in_the_filtered_list() throws Throwable {
		System.out.println("Number of Count: " + flightInfo.getFlightPrices().size());
		test.log(Status.INFO, "Got number of flight count");
	}

	@When("^User retrieves price details from each flight$")
	public void user_retrieves_price_details_from_each_fligh() throws Throwable {
		try {
			flightPrices = flightInfo.getFlightPrices();

			for (WebElement price : flightPrices) {
				String value = price.getText().replace("₹ ", "").replace(",", "");
				pricesList.add(Integer.parseInt(value));
			}
			Lowest_Price = Collections.min(pricesList);
			System.out.println("Lowest Price is: Rs. " + Lowest_Price);
		} catch (Exception e) {
			System.out.println("Empty price list");
		}
	}

	@When("^User select the fastest flight from the filtered list$")
	public void user_select_the_fastest_flight_from_the_filtered_list() throws Throwable {
		try {
			flightDurations = flightInfo.getflightDurations();
			for (WebElement price : flightDurations) {
				String duration = price.getText().replace(" h ", "").replace(" h ", "").replace(" m ", "")
						.replace(" m ", "").trim();
				DuraionList.add(duration);
			}

			Minimum_Duration = Collections.min(DuraionList).replace("m", "").replace(" ", "");
			System.out.println("Fastest Flight in hrs: " + Minimum_Duration);
		} catch (Exception e) {
			System.out.println("Empty Duration list");
		}
	}

	@When("^User select the evening flight if duplicate set of lowest price and fastest flight$")
	public void user_select_the_evening_flight_if_duplicate_set_of_lowest_price_and_fastest_flight() throws Throwable {
		UtilFunctions.getFlightInfo();
		int parseInt = Integer.parseInt(Minimum_Duration);
		Flight_Name = UtilFunctions.readFlightNameFromData(Lowest_Price, parseInt);
		System.out.println("Flight Name is: " + Flight_Name);
	}

	@Then("^User verifies that the selected option is lowest and fastest$")
	public void user_verifies_that_the_selected_option_is_lowest_and_fastest() throws Throwable {

		System.out.println("\t" + "Price  " + "          Duration");
		for (int i = 0; i < flightPrices.size(); i++) {

			System.out.print("\t" + flightPrices.get(i).getText().replace("₹", "Rs.") + "     ");
			System.out.println("\t" + flightDurations.get(i).getText());
		}
		test.log(Status.INFO, "Retreived all the Flight Info");
	}
}
