package com.makemytrip.stepdefinition;

import java.io.IOException;

import com.makemytrip.baseclass.BaseClass;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks extends BaseClass {
	@Before
	public void beforeHooks(Scenario scenario) {
		String name = scenario.getName();
		System.out.println("Scenario: " + name);
	}

	@After
	public void afterHooks(Scenario scenario) throws IOException {
		String status = scenario.getStatus();
		System.out.println("      Status: " + status+"\n\n");

		if (scenario.isFailed()) {
			takeScreenShot(scenario.getName());
		}
	}
}
