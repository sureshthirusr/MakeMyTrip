package com.makemytrip.stepdefinition;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import com.aventstack.extentreports.Status;
import com.makemytrip.baseclass.BaseClass;
import com.makemytrip.pom.*;
import com.makemytrip.runner.RunWithChrome;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;

public class Booking_Flight_StepDefinition extends BaseClass {

	public static WebDriver driver = RunWithChrome.driver;
	public static SelectFlightPage fp = new SelectFlightPage(driver);
	public static AllFlightInfoPage ap = new AllFlightInfoPage(driver);
	public static Properties prop = RunWithChrome.prop;
	JavascriptExecutor executor = (JavascriptExecutor) driver;
	Actions ac = new Actions(driver);
	public static String SelectedFlightName;

	@Given("^User view the info of the selected flight$")
	public void user_view_the_selected_flight() throws Throwable {
		SelectedFlightName = Lowest_Fastest_Selection_StepDefinition.Flight_Name;
		System.out.println("\nThe Selected Flight Name is : " + SelectedFlightName);
	}

	@When("^User click on radio button of selected flight$")
	public void user_click_on_radio_button_of_selected_flight() throws Throwable {
		test = extent.createTest("Book the flight");
		test.assignCategory("Regression Test");

		pageLoadWait();
		/*
		 * ac.moveToElement(fp.getselectFlight()).click().perform(); Thread.sleep(2000);
		 * executor.executeScript("arguments[0].click();", fp.getselectFlight());
		 * System.out.println("Flight selected"); test.log(Status.INFO,
		 * "Flight Selected");
		 */
		try {
			for (int j = 0; j < ap.getFlightName().size(); j++) {
				WebElement flight = ap.getFlightName().get(j);
				if (flight.getText().equals(SelectedFlightName)) {
					ac.moveToElement(flight).click().perform();
					executor.executeScript("arguments[0].click();", flight);
				}
			}
			Thread.sleep(2000);
			System.out.println("Flight selected");
			test.log(Status.INFO, "Flight Selected");
		} catch (Exception e) {
			System.out.println("Unable to Select");
		}
	}

	@When("^User click on Book button for booking the same flight$")
	public void user_click_on_Book_button_for_booking_the_same_flight() throws Throwable {

		try {
			pageLoadWait();
			executor.executeScript("arguments[0].click();", fp.getbookNow_Button());
			Thread.sleep(5000);
			executor.executeScript("arguments[0].click();", fp.getcontinueBtn());

			System.out.println("Flight Booked");
			test.log(Status.INFO, "Flight Booked");
			}

		catch (Exception e) {
			throw new Exception("Unable to book");
		}
	}

	@Then("^User verifies the booking details$")
	public void user_verifies_the_booking_details() throws Throwable {

		try {
			pageLoadWait();
			String wId = driver.getWindowHandle();
			BaseClass.windowsHandling(wId);
			int size = fp.getreviewdFlightName().size();
			String BookedFlightName = "";
			for (int i = 0; i < size; i++) {
				String FlightName = fp.getreviewdFlightName().get(i).getText();
				BookedFlightName = BookedFlightName + FlightName;
			}
			System.out.println("Selectecd Flight Name :" + SelectedFlightName.replace(" |", ""));
			System.out.println("Booked Flight Name :" + BookedFlightName.replace(" |", ""));
			
			String actual = SelectedFlightName.replace(" |", "");
			String expected = BookedFlightName.replace(" |", "");
			assertTrue(expected.contains(actual));
			System.out.println("Validation completed Successfully");
			test.log(Status.INFO, "Validation completed Successfully");

		} catch (Exception e) {
			throw new Exception("validation didnt complete");
		}
	}
}
