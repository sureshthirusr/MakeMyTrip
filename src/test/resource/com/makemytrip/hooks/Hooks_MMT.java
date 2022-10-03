package com.makemytrip.hooks;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks_MMT {
	
	@Before
	private void beforeHooks(Scenario scenario) {
		System.out.println(scenario.getName());
	}
	
	@After
	private void afterHooks(Scenario scenario) {
		String status = scenario.getStatus();
		System.out.println(scenario.getStatus());
		if(status=="failed") {
			
		}
	}

}
