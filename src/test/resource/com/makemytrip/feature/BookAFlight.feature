Feature: Testing booking functionality in the makemytrip application

Scenario: user search a flight using valid search details
Given User launch the application
When User selects the flight option to travel
And User checks on round trip as option
And User enter "Chennai, India" as source city
And User enter "Delhi, India" as destination city
And User enter the departure date
And User enter the return date
And User click on the search button
Then User navigates to Flight details page

Scenario: User filter the flights that has the lowest price and minimul travelling time  
Given user view number of flights with unordered
When User filter the low to high option from the drop down
And User counts the number of flights in the filtered list
And User retrieves price details from each flight
And User select the fastest flight from the filtered list
And User select the evening flight if duplicate set of lowest price and fastest flight
Then User verifies that the selected option is lowest and fastest

Scenario: User select the flight the lowest price, fastest and book the same
Given User view the info of the selected flight
When User click on radio button of selected flight
And User click on Book button for booking the same flight
Then User verifies the booking details

